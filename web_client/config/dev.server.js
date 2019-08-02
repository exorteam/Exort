let bodyParser = require('body-parser');
var cookieParser = require('cookie-parser')

const api = require('axios').create({
    baseURL: 'http://localhost:8080'
});

let sessions = [];
let session_max_id = 0;

function get_sess_id(req) {
    return req.cookies && req.cookies.sess_id;
}

function new_sess(sess) {
    session_max_id++;
    sessions[session_max_id] = sess;
    return session_max_id;
}

module.exports = {
    before: function(app, server) {
        // for parsing application/json
        app.use(bodyParser.json());
        // for parsing application/x-www-form-urlencoded
        app.use(bodyParser.urlencoded({ extended: true }));
        // for parsing cookies
        app.use(cookieParser())

        app.post('/auth/logout', (req, res) => {
            let sess_id = get_sess_id(req);
            console.log('[Logout] ' + sess_id);
            if (sessions[sess_id]) {
                delete sessions[sess_id];
            }
            res.status(200).send();
        });

        app.post('/auth/login', async (req, res) => {
            try {
                console.log('[Login]' + JSON.stringify(req.body));
                const {username, password} = req.body;
                const apires = await api({
                    method: 'post',
                    url: '/auth/login',
                    data: {
                        'username': username,
                        'password': password
                    }
                });

                let sess_id = new_sess(apires.data.rtoken);
                console.log('new session: ' + sess_id);
                res.cookie('sess_id', sess_id, {httpOnly: true});
                res.status(200).json({token: apires.data.token, uid: apires.data.uid});
            } catch (err) {
                if (err.response && err.response.data) {
                    res.status(401).json(err.response.data);
                } else {
                    res.status(500).json(err);
                }
            }
        });

        app.get('/auth/token', async (req, res) => {
            let sess_id = get_sess_id(req);
            console.log('[Token] ' + sess_id);
            if (sessions[sess_id]) {
                try {
                    console.log('old rtoken: ' + sessions[sess_id]);
                    const apires = await api({
                        method: 'post',
                        url: '/auth/token',
                        data: {
                            'rtoken': sessions[sess_id]
                        }
                    });
                    console.log('new rtoken: ' + apires.data.rtoken);
                    sessions[sess_id] = apires.data.rtoken;
                    res.status(200).json({token: apires.data.token, uid: apires.data.uid, username: apires.data.username});
                } catch (err) {
                    console.log('token failed');
                    delete sessions[sess_id];
                    if (err.response && err.response.data) {
                        res.status(401).json(err.response.data);
                    } else {
                        res.status(500).json(err);
                    }
                }
            } else {
                console.log('not login');
                res.status(401).json({error: 'not_login', message: 'Not login.'});
            }
        });
    }
}
