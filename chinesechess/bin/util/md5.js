var crypto = require('crypto');
var token = '中国象棋象棋中国';

var md5 = function(content){
    var c = crypto.createHash('md5');
    content+=token;
    c.update(content);
    return c.digest('hex');
}

module.exports = md5;