How to scrape doordash


## Doordash scrape

javascript:(function() {
    function l(u, i) {
        var d = document;
        if (!d.getElementById(i)) {
            var s = d.createElement('script');
            s.src = u;
            s.id = i;
            d.body.appendChild(s);
        }
    }
    l('//code.jquery.com/jquery-3.2.1.min.js', 'jquery')
})();

var menu = [];
$('[class^=Category_itemContainer').each(function(i, obj) {
	var cat,name,desc,price,imageUrl;
	cat      = $(obj).parents("[class^=Category_root]").find("[class^=Category_name]").text();
	name     = $(obj).find("[class^=Item_name]").text();
	price    = $(obj).find("[class^=Item_price]").text();
	price    = price.trim().substring(1);
	if (price.length==0)continue;
	desc     = $(obj).find("[class^=Item_description]").text().trim();
	if (desc.length==0){
		desc = null;
	}
	imageUrl = $(obj).find("[class^=Item_image]").css("background-image")
	if (typeof imageUrl!="undefined" && imageUrl.startsWith("url(")){
		imageUrl = imageUrl.substring(5,imageUrl.length-2);
	}
	txt = $(obj).text();
	if (txt.indexOf("$")!=-1){
    	var data = txt.split("$");
    	var res = {
    		groupName:cat,
    		name:name,
    		price:price,
    		description:desc,
    		imageUrl:imageUrl || null
    	};
    	menu.push(res)
	}
});
copy(JSON.stringify(menu))