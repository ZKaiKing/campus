//请求函数封装
let commonfun = {
    request : (url,data,callback,failfun)=>{
        $.ajax({
        url:url,
        type:"post",
        data:data,
        dataType:"json",
        success:(data)=>{
            callback(data)
        },
        fail:(err)=>{
            failfun(err)
        }
        })
    }
}


/**
 * [userstorage 存储localstorage时候最好是封装一个自己的键值，在这个值里存储自己的内容对象，封装一个方法针对自己对象进行操作。避免冲突也会在开发中更方便。]
 * @param  {String} ){                 var ms [description]
 * @return {[type]}     [description]
 console.log(userstorage.set('tqtest','tqtestcontent'));//存储
 console.log(userstorage.set('aa','123'));//存储
 console.log(userstorage.set('tqtest1','tqtestcontent1'));//存储
 console.log(userstorage.set('tqtest1','newtqtestcontent1'));//修改
 console.log(userstorage.get('tqtest'));//读取
 console.log(userstorage.remove('tqtest'));//删除
 userstorage.clear();//整体清除
 */
var userstorage = (function userstorage(){
    var ms = "userstorage";
    var storage=window.sessionStorage;
    if(!window.localStorage){
        alert("浏览器不支持localstorage");
        return false;
    }
    var set = function(key,value){
        //存储
        storage.setItem(key,value);
        return value;
    };
    var get = function(key){
        //读取
        var value = storage.getItem(key);
        if(!key){
            return false;
        }
        return value;
    };
    var remove = function(key){
        //读取
        var value = storage.getItem(key);
        if(!value){
            return false;
        }
        storage.removeItem(key);
        return true;
    };
    return {
        set : set,
        get : get,
        remove : remove
    };
})();
   
