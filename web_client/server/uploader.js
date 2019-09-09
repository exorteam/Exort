var crypto = require('crypto');
var fs = require('fs');
var path = require('path');
var multer  = require('multer')

exports.saveFile = function(buffer, ext) {
    var fsHash = crypto.createHash('md5');
    fsHash.update(buffer);
    var filename = fsHash.digest('hex') + (ext || '');

    var stream = fs.createWriteStream(
        path.join(__dirname, 'public', 'files', filename)
    );
    stream.write(buffer)
    stream.end();

    return filename;
}

exports.filter = function(fileSize, mimetype) {
    return multer({
        storage: multer.memoryStorage(),
        limits: { files: 1, fileSize: fileSize },
        fileFilter: function(req, file, cb) {
            cb(null, mimetype(file.mimetype));
        }
    }).single('file');
}
