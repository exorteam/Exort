const bodyParser = require('body-parser');
const cookieParser = require('cookie-parser');

const api = require('axios').create({
    baseURL: 'http://localhost:8080'
});

const proxy = (function(paths) {
    let a = {};
    for (let i in paths) {
        a[paths[i]] = 'http://localhost:3000';
    }
    return a;
})(['/files', '/upload'])

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
    proxy: proxy,
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

                let sess_id = new_sess(apires.data.data.rtoken);
                console.log('new session: ' + sess_id);
                res.cookie('sess_id', sess_id, {httpOnly: true});
                res.status(200).json({token: apires.data.data.token, uid: apires.data.data.uid});
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
                    console.log('new rtoken: ' + apires.data.data.rtoken);
                    sessions[sess_id] = apires.data.data.rtoken;
                    res.status(200).json((({token, uid, username}) => ({token, uid, username}))(apires.data.data));
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
