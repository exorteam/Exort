var target = { 'target': process.env.PROXY_TARGET };

module.exports = {
    assetsDir: 'static',
    pages: {
        index: {
            entry: 'src/main.js',
            template: 'index.html',
            filename: 'index.html'
        }
    },
    outputDir: 'server/public',
    productionSourceMap: false,
    devServer: {
        proxy: {
            '/files': target,
            '/upload': target,
            '/auth': target
        }
    }
}
