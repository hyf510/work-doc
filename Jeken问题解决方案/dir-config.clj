; "目录配置及规则配置，如有疑问请联系liujun842"

; --------------------------------
; @author:	liujun842
; @mail:	liujun842@pingan.com.cn
; --------------------------------

(fn [env] 
{
	; 全量包需要的文件
	:all-file-regex [
		;项目dist目录所需文件正则，括号内为需要保留的目录结构
		#".+/dist/(.+\.html)$"  
		#".+/dist/(js/.+\.js)$"	
		#".+/dist/(css/.+\.css)$" 
		#".+/dist/(images/.+\.(?:png|jpg|ico|gif))$" 
		#".+/dist/(libs/.+\.js)$" 
		#".+/dist/(test/.+\.html)$" 
	]
	:mapping [
		; 全量文件名正则				需要移动的条件，匹配svn/git改动文件清单									移动目标路径
		[#".+/(.+)\.html" 			["include/_header.ejs" "include/_footer.ejs" "templates/$1.ejs"] 	"$1.html"]
		; less编译成css后会合并的模块需要启用并配置该规则
		#".+/css/(BT).css" 		[#"less/.+\.less"] 													"css/BT.css"
		[#".+/images/(.+)" 			["www/images/$1"]													"images/$1"]
		[#".+/js/(.+)\.js" 			["www/js/$1.js"]													"js/$1.js"]
		; 生产包时 common下的任意文件改动 都需要拷贝common.js
		[#".+/js/common/common\.js" (if (= "PRODUCTION" env)  [#"www/js/common/.+\.js"] [])				"js/common/common.js"]
		; 增量包需要考虑libs下的文件改动需要启用并配置该规则
		; #".+/libs/.+\.js"			[]					
	]
	;模块历史增量包目录
	:prj-update-dir "upgrade/update/"
	:prj-dist "dist/"
})