/**
 * 校验
 */
/** 
 * 返回非空字符串,如果有默认值就返回默认字符串. 
 * @param {要进行转换的原字符串} str 
 * @param {默认值} defaultStr 
 * @return {返回结果} 
 */  
function notNull(str, defaultStr) {  
        if (typeof(str) == "undefined" || str == null || str == '') {  
                if (defaultStr)  
                        return defaultStr;  
                else  
                        return '';  
        } else {  
                return str;  
        }  
}

/** 
 * 比较两个日期大小. 
 * @param {较小日期的文本框id} smallDate 
 * @param {较大日期的文本框id} bigDate 
 * @param {出错的提示信息} msg 
 */  
function compareTwoDate(smallDate, bigDate, msg) {  
        var v1 = $(smallDate).value;  
        var v2 = $(bigDate).value;  
        if (v1 >= v2) {  
                alert(msg);  
                v2.focus();  
                return false;  
        }  
        return true;  
} 

/** 
 * 比较两个金额大小的方法. 
 * @param {较小的金额} smallNum 
 * @param {较大的金额} bigNum 
 * @param {出错提示信息} msg 
 * @return {Boolean} 
 */  
function compareTwoNum(smallNum, bigNum, msg) {  
        var v1 = $(smallNum).value;  
        var v2 = $(bigNum).value;   
        if (parseFloat(v1) >= parseFloat(v2)) {  
                alert(msg);  
                v2.focus();  
                return false;  
        }  
        return true;  
} 

/** 
 * 检查文本框的长度是否超出指定长度. 
 * @param {文本id} textId 
 * @param {文本框的最大长度} len 
 * @param {文本框描述内容} msg 
 * @return {有错就返回false,否则返回true} 
 */  
function checkLength(textId, len, msg) {  
        obj = $(textId);  
        str = obj.value;  
        str = str.replace(/[^\x00-\xff]/g, "**");  
        realLen = str.length;  
        if (realLen > len) {  
                alert("[" + msg + "]" + "长度最大为" + len + "位," + "请重新输入！\n注意：一个汉字占2位。");  
                obj.focus();  
                return false;  
        } else  
                return true;  
} 

/** 
 * 判断某个文本框不可以为空. 
 * @param {文本框id} textId 
 * @param {文本框描述内容} msg 
 * @return {有错就返回false,否则返回true} 
 */  
function checkIfEmpty(textId, msg) {  
        var textObj = $(textId);  
        var textValue = textObj.value;  
        if (trim(textValue) == '') {  
                alert('[' + msg + ']不得为空！');  
                textObj.focus();  
                return false;  
        } else {  
                return true;  
        }  
} 

/** 
 * 判断某个文本框是否数字. 
 * @param {文本框id} textId 
 * @param {文本框描述内容} msg 
 * @return {Boolean} 
 */  
function checkIsNum(textId, msg) {  
        obj = $(textId);  
        if (isNaN(obj.value)) {  
                alert('[' + msg + ']必须为数字。');  
                obj.focus();  
                return false;  
        } else  
                return true;  
}

/** 
 * 判断某个文本框是否含有非法字符. 
 * @param {文本框的id} textId 
 * @param {文本框描述内容} msg 
 * @return {有错就返回false否则返回true} 
 */  
function checkIsValid(textId, msg) {  
        obj = $(textId);  
        if (!_isValidString(obj.value, '[' + msg + ']不得含有非法字符。')) {  
                obj.focus();  
                return false;  
        }  
        return true;  
}  
  
/** 
 * 判断是不是合法字符串. 
 * @param {要进行判断的字符串} szStr 
 * @param {文本描述} errMsg  
 * @return {合法则返回true否则返回false} 
 */  
function _isValidString(szStr,errMsg) {  
        voidChar = "'\"><`~!@#$%^&\(\)（）！￥……？?“”‘’*";  
        for (var i = 0; i < voidChar.length; i++) {  
                aChar = voidChar.substring(i, i + 1);  
                if (szStr.indexOf(aChar) > -1){  
                        alert(errMsg)  
                        return false;  
                }  
        }  
        return true;  
}