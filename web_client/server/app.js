var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');
var history = require('connect-history-api-fallback');
var uploader = require('./uploader');

var app = express();

app.set('case sensitive routing', true);
app.set('trust proxy', true);

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());

app.use(express.static(path.join(__dirname, 'public')));
app.use(history({ disableDotRule: true }));
app.use(express.static(path.join(__dirname, 'public')));

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

module.exports = app;
