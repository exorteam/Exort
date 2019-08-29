const bodyParser = require('body-parser');
const cookieParser = require('cookie-parser');
const session = require('express-session')

const api = require('axios').create({
    baseURL: process.env.VUE_APP_API_BASE
});

const proxy = (function(paths) {
    let a = {};
    for (let i in paths) {
        a[paths[i]] = {
            'target': process.env.PROXY_TARGET
        };
    }
    return a;
})(['/files', '/upload']);

module.exports = {
    outputDir:'server/public',
    productionSourceMap: false,
    devServer: {
        proxy: proxy,
        before: function(app, server) {
            // for parsing application/json
            app.use(bodyParser.json());
            // for parsing application/x-www-form-urlencoded
            app.use(bodyParser.urlencoded({ extended: true }));
            // for parsing cookies
            app.use(cookieParser())
            // for sessions
            app.use(session({
                secret: 'Exort Web Client Dev Server',
                rolling: true,
                resave: false,
                saveUninitialized: false
            }))

            app.post('/auth/logout', (req, res) => {
                console.log('[Logout] ' + req.session.id);
                req.session.destroy(function(err) {});
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

                    console.log('new session: ' + req.session.id);
                    req.session.rtoken = apires.data.data.rtoken;
                    res.status(200).json((({token, uid}) => ({token, uid}))(apires.data.data));
                } catch (err) {
                    if (err.response && err.response.data) {
                        res.status(401).json(err.response.data);
                    } else {
                        res.status(500).json(err);
                    }
                }
            });

            app.get('/auth/token', async (req, res) => {
                console.log('[Token] ' + req.session.id);
                if (req.session.rtoken) {
                    try {
                        console.log('old rtoken: ' + req.session.rtoken);
                        const apires = await api({
                            method: 'post',
                            url: '/auth/token',
                            data: {
                                'rtoken': req.session.rtoken
                            }
                        });
                        console.log('new rtoken: ' + apires.data.data.rtoken);
                        req.session.rtoken = apires.data.data.rtoken;
                        res.status(200).json((({token, uid, username}) => ({token, uid, username}))(apires.data.data));
                    } catch (err) {
                        console.log('token failed');
                        req.session.destroy(function(err) {});
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
}
