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
            '/files': { target: process.env.PROXY_TARGET },
            '/upload': { target: process.env.PROXY_TARGET },
            '/auth': { target: process.env.AUTH_PROXY_TARGET }
        }
    }
}
