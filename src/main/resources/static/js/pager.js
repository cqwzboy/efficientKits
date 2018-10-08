//var offset = $("#offset").val();
//var limit = $("#limit").val();
//var total = $("#total").val();
//当前页码
//var currentPage = parseInt(offset/limit) + 1;
//总页数
//var totalPage = total%limit!=0 ? parseInt(total/limit)+1 : parseInt(total/limit);

var currentPage = 5;
var totalPage = 9;

$(document).ready(function () {
    if(totalPage == 0){
        $("._pager_").html('');
    }else{
        var content = '<div class="mod-pagination text-center">';
        if(currentPage == 1){
            content += '<button type="button" class="btn-first disabled">首页</button><button type="button" class="btn-prev disabled">上一页</button>';
        }else{
            content += '<button type="button" class="btn-first">首页</button><button type="button" class="btn-prev">上一页</button>';
        }

        content += '<ul class="pager">'

        function buildContent(i, loop) {
            var builder = '';
            for(;i<=loop;i++){
                if(i == currentPage){
                    builder += '<li class="number active li_style">' + i + '</li>';
                }else{
                    builder += '<li class="number li_style">' + i + '</li>';
                }
            }

            return builder;
        }

        if(totalPage<=8){//当totalPage在1-8之间时
            content += buildContent(1, totalPage);
        }else{
            if(currentPage==1 || currentPage==2 || currentPage==3 || currentPage==4){//第一格子始终为首页页码1
                content += buildContent(1, 9);
            }else if(currentPage==totalPage || currentPage==totalPage-1 || currentPage==totalPage-2 || currentPage==totalPage-3){//最后一格子始终为末页页码
                content += buildContent(totalPage-8, totalPage);
            }else{//当前页始终在九个格子的中间
                content += buildContent(currentPage-4, currentPage+4);
            }
        }

        content += '</ul>'

        if(currentPage == totalPage){
            content += '<button type="button" class="btn-next disabled">下一页</button><button type="button" class="btn-last disabled">尾页</button>';
        }else{
            content += '<button type="button" class="btn-next">下一页</button><button type="button" class="btn-last">尾页</button>';
        }

        content += '<span class="pagination-total">共' + totalPage + '页</span>' +
            '<span class="pagination-jump">到<input type="number" min="1" value="'+ currentPage + '" number="true" class="pagination-editor">页' +
            '<button type="button" class="btn-jump">Go</button></span></div>'

        $("._pager_").html(content);
    }
});

/**
 * 获取查询参数，此参数需要从各自引用的页面分别实现
 * */
function getParams() {
    var params = buildParams();
    var parameters = '';
    for(var key in params){
        parameters += "&" + key + "=" + params[key];
    }
    return parameters;
}

/**
 * 跳转
 * */
function send(parameters) {
    window.location.href = $("#pagingPath").val() + parameters;
}

//首页
$("._pager_").on('click', '.btn-first', function () {
    if($(this).hasClass('disabled')){
        return;
    }
    var parameters = "?offset=0" + getParams();
    send(parameters);
});

//上一页
$("._pager_").on('click', '.btn-prev', function () {
    if($(this).hasClass('disabled')){
        return;
    }
    if(currentPage == 1){
        return;
    }
    offset = (currentPage-2) * limit;
    var parameters = "?offset=" + offset + getParams();
    send(parameters);
});

//点击具体页码
$("._pager_").on('click', '.number', function () {
    var targetPage = $(this).html();
    offset = (targetPage-1) * limit;
    var parameters = "?offset=" + offset + getParams();
    send(parameters);
});

//下一页
$("._pager_").on('click', '.btn-next', function () {
    if($(this).hasClass('disabled')){
        return;
    }
    if(totalPage == currentPage){
        return;
    }
    offset = currentPage * limit;
    var parameters = "?offset=" + offset + getParams();
    send(parameters);
});

//尾页
$("._pager_").on('click', '.btn-last', function () {
    if($(this).hasClass('disabled')){
        return;
    }
    var parameters = "?offset=" + (totalPage-1)*limit + getParams();
    send(parameters);
});

//跳转至某一页
$("._pager_").on('click', '.btn-jump', function () {
    var jumpToNum = $(".pagination-editor").val();
    if(jumpToNum==null || parseInt(jumpToNum)<=0 || parseInt(jumpToNum)>totalPage){
        return;
    }
    offset = (parseInt(jumpToNum)-1) * limit;
    var parameters = "?offset=" + offset + getParams();
    send(parameters);
});

function log(obj) {
    console.log(obj);
}
