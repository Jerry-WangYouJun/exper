
function initMenu(id, obj){
//  var datalist = $("#" + id);
 // var data = eval("(" + obj + ")");
	var data = obj;
//  datalist.append("<div id='result' style='display:none'></div>");
  // var resultArea = datalist.find("#result");
  // datalist.append("<div class='menu' id='firstMenu' >" + "" + "</div>");

  var firstMenu = $("#firstMenu");
  firstMenu.html("");
  firstMenu.append("<div id='result' style='display:none'></div>");
  var resultArea = firstMenu.find("#result");
  var secondMenu = $("#secondMenu"); 
  firstMenu.append("<ul></ul>");
  $.each(data, function (i, obj) {   // 循环第一级
      $(firstMenu).find("ul").append("<li id='dl_" + i + "' name='" + i + "'>" + obj.product + "</li>");
        // if (datalist.find("div[class='sub-menu']").length <= 0) {
        //     datalist.append("<div class='sub-menu' id='secondMenu'></div>");
        // }
        //var secondMenu = datalist.find("#secondMenu");
        $("#firstMenu ul li").bind("click", function () {
          secondMenu.html("");
          $( "#firstMenu ul li").removeClass("sele");
          $(this).addClass("sele");
          var index = $(this).attr("name");
          $.each(data[index].children, function (j, item) {
            secondMenu.append("<dl id='dl_" + j + "'></dl>");
            var dtItem = "<dt id='dt_" + j + "'>" + item.type + "</dt>";
            secondMenu.find("dl[id='dl_" + j + "']").append(dtItem);
            secondMenu.find("dl[id='dl_" + j + "']").append("<dd id='dd_" + j + "'></dd>");
            $.each(data[index].children[j].children, function (m, dist) {
            	if(dist.selected!=null&&dist.selected==1){
                    var threeMenu = "<a class='sele' href='javascript:void(0)' id='" +  dist.id  + "'>"
                    + dist.subProduct + ' - ' + dist.format + ' - ' + dist.material + "</a>";
                     resultArea.find("a[id='c_" + dist.id + "']").remove();
                     resultArea.append("<a href='#' id='c_" +dist.id + "'>" +dist.id + "</a>");
                  }else{
                    var threeMenu = "<a href='javascript:void(0)' id='" +  dist.id  + "'>"
                    + dist.subProduct + ' - ' + dist.format + ' - ' + dist.material + "</a>";
                }
              secondMenu.find("dl[id='dl_" + j + "'] dd[id='dd_" + j + "']").append(threeMenu);
            });
          });
          var resultItems = resultArea.find("a");
            $.each(resultItems, function (n, ritem) {
                var rid = $(ritem).attr("id").substr(2, $(ritem).attr("id").length);
                secondMenu.find("a[id='" + rid + "']").addClass("sele");
            });
            //       // 列表项点击事件，选中则在结果容器中显示添加选中样式
             secondMenu.find("a").bind("click", function () {
               if ($(this).hasClass("sele")){
                 $(this).removeClass("sele");
                 resultArea.find("a[id='c_" + $(this).attr("id") + "']").remove();
               }else{
                 $(this).addClass("sele");
                 resultArea.append("<a href='#' id='c_" + $(this).attr("id") + "'>" + $.trim($(this).attr("id")) + "</a>");
               }
            });
        });
        $(firstMenu).find("ul li:first").addClass("sele").trigger("click");
  });

}
function setSelected(){
  var resultArea = datalist.find("#result");

}
function getSelected(){
	var firstMenu = $("#firstMenu");
	var resultArea = firstMenu.find("#result");
    var selectedItems = resultArea.find("a");
    var results = "";
    $.each(selectedItems, function (i, item) {
        results += $.trim($(item).html()) + ",";
    });
    if (results.length > 0) {
      results = results.substr(0, results.length - 1);
    }
    return results;
}
