var express = require('express');
var path = require('path');
var bodyParser = require('body-parser');
var cookieParser = require('cookie-parser');
var session = require('express-session')
var logger = require('morgan');
var history = require('connect-history-api-fallback');
var axios = require('axios');
var uploader = require('./uploader');

if (!process.env.SESSION_SECRET) {
    throw Error('Environment variable SESSION_SECRET must be set!');
}

var api = axios.create({baseURL:process.env.API_BASE_URL || 'http://exort-api'});
api.defaults.headers.common['Content-Type'] = 'application/json';


var app = express();

app.set('case sensitive routing', true);
app.set('trust proxy', true);

app.use(logger('dev'));
app.use(express.json());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(session({
    secret: process.env.SESSION_SECRET,
    rolling: true,
    resave: false,
    saveUninitialized: false
}));

app.post(
    '/upload/image',
    uploader.filter(
        512*1024, // Byte
        function(mimetype) {
            return mimetype.startsWith('image/');
        }
    ),
    function(req, res) {
        res.json({
            filename: uploader.saveFile(
                req.file.buffer,
                path.extname(req.file.originalname)
            )
        });
    }
);
app.post(
    '/upload/text',
    uploader.filter(
        512*1024, // Byte
        function(mimetype) {
            return mimetype == 'text/plain';
        }
    ),
    function(req, res) {
        res.json({
            filename: uploader.saveFile(req.file.buffer, '.txt')
        });
    }
);

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
        if (apires.data.data) {
            console.log('new session: ' + req.session.id);
            req.session.rtoken = apires.data.data.rtoken;
            res.status(200).json((({token, uid}) => ({token, uid}))(apires.data.data));
        } else {
            res.status(401).json(apires.data);
        }
    } catch (err) {
        console.log(err);
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
            console.log(err);
            req.session.destroy(function(err) {});
            if (err.response && err.response.data) {
                res.status(401).json(err.response.data);
            } else {
                res.status(500).json(err);
            }
        }
    } else {
        console.log('not login');
        res.status(401).json({error: 'not_login', message: '未登录'});
    }
});

app.use(express.static(path.join(__dirname, 'public')));
app.use(history({ disableDotRule: true }));
app.use(express.static(path.join(__dirname, 'public')));

module.exports = app;
