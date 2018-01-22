/**
 * this file is called in input.js
 * @param data
 * @returns
 */
function dataFunction(data){
				//请求完毕后完成的工作，需要获取json数据
				//如何获取json数据?
				//获取到json数据后，需要将其转换为select中的数据
				var gtmList = data.gtmList;
				for(var i = 0;i<gtmList.length;i++){
					var gtm = gtmList[i];
					//alert(gtm.uuid+","+gtm.name);
					//将json数据中的每个项转换成select中的option选项
					var $op = $("<option value='"+gtm.uuid+"'>"+gtm.name+"</option>");
					//然后添加到select中
					$("#goodsType").append($op);
				}
			}
