package com.pbt.myfarm.Activity.Testing

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import com.pbt.myfarm.Activity.Event.ResponsefieldClasses
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.databinding.ActivityDemoHtmlToPdfBinding
import okhttp3.Callback
import okhttp3.ResponseBody
//import org.jsoup.Jsoup
//import org.jsoup.select.Elements
//import org.nvest.html_to_pdf.HtmlToPdfConvertor
import org.w3c.dom.Document
import retrofit2.Call
import retrofit2.Response
import java.io.File


class DemoHtmlToPdfActivity : AppCompatActivity(),retrofit2.Callback<ResponsefieldClasses> {
//    private lateinit var htmlToPdfConvertor: HtmlToPdfConvertor
    private lateinit var binding: ActivityDemoHtmlToPdfBinding
    var myHtmlContext:String=""
    val TAG="DemoHtmlToPdfActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDemoHtmlToPdfBinding.inflate(layoutInflater)
        setContentView(binding.root)

        callAPi()




        // initialize HtmlToPdfConvertor
//        htmlToPdfConvertor = HtmlToPdfConvertor(this)

        // set base url
//        htmlToPdfConvertor.setBaseUrl("file:///android_asset/images/")

        binding.btnConvert.setOnClickListener {
            if (shouldAskPermission()) {
                requestPermissions(
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    STORAGE_PERMISSION_REQUEST
                )
            } else {
                convertHtmlToPdf()
            }
        }

    }

    private fun callAPi() {
        val myHtmlCall: Call<ResponseBody> = ApiClient.client.create(
            ApiInterFace::class.java
        ).printPdf(
          "46"
        )

        myHtmlCall.enqueue(myHTMLResponseCallback)
    }
    private val myHTMLResponseCallback: retrofit2.Callback<ResponseBody?> =
        object : retrofit2.Callback<ResponseBody?> {

//            fun onFailure(call: Call<ResponseBody?>?, t: Throwable) {
//                t.printStackTrace()
//            }

            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                try {
                    val html = response.body()!!.string()
                    Log.d(TAG, " myHTMLResponseCallbackresponseee  : "+html)

//                    val document: org.jsoup.nodes.Document? = Jsoup.parse(html)
//                    val elements: Elements = document?.select("name_of_tag_you want_to_get")
//                    for (element in elements) {
//                        if (element.attr("name_of_attribute_you want to check")
//                                .equals("value_of_the_attribute")
//                        ) {
//                            Save As you want to
//                            Log.d(TAG, " myHTMLResponseCallback : " + element.attr("value"))
//                        }
//                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {

            }
        }

    override fun onRequestPermissionsResult(
                requestCode: Int,
                permissions: Array<out String>,
                grantResults: IntArray
            ) {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
                if (requestCode == STORAGE_PERMISSION_REQUEST) {
                    if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        convertHtmlToPdf()
                    } else {
                        Toast.makeText(
                            this,
                            "Storage permission denied, enabled it from settings",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            private fun convertHtmlToPdf() {
                // start loading
                binding.loader.isVisible = true

                // define output file location and html content
                val pdfLocation = File(getPdfFilePath(), "${System.currentTimeMillis()}.pdf")
                val htmlString = htmlContent()

                // start conversion
//                htmlToPdfConvertor.convert(
//                    pdfLocation = pdfLocation,
//                    htmlString = htmlString,
//                    onPdfGenerationFailed = { exception ->
//                        // something went wrong, stop loading and log the exception
//                        binding.loader.isVisible = false
//                        exception.printStackTrace()
//                        Toast.makeText(this, "Check logs for error", Toast.LENGTH_SHORT).show()
//                    },
//                    onPdfGenerated = { pdfFile ->
//                        // pdf was generated, stop the loading and open it
//                        binding.loader.isVisible = false
//                        openPdf(pdfFile)
//                    })
            }

            private fun shouldAskPermission(): Boolean {
                return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                        checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            }

            private fun openPdf(pdfFilePath: File) {
                try {
                    val path = FileProvider.getUriForFile(
                        this,
                        "$packageName.fileprovider",
                        pdfFilePath
                    )
                    val pdfIntent = Intent(Intent.ACTION_VIEW)
                    pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    pdfIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    pdfIntent.setDataAndType(path, "application/pdf")
                    startActivity(pdfIntent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                    Toast.makeText(this, "No application found to open pdf file", Toast.LENGTH_SHORT).show()
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                    Toast.makeText(
                        this,
                        "Unable to open ${pdfFilePath.name}, please try again later.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            private fun getPdfFilePath(): File? {
                return when {
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
                        getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
                    }
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT -> {
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                    }
                    else -> {
                        File(Environment.getExternalStorageDirectory().toString() + "/Documents/")
                    }
                }
            }

            private fun htmlContent() =
                """
       <!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="utf-8">
	<!--<title>NAIR IP</title>-->
	<title>CERTIFICATE OF KAKAOMUNDO TRACEABILITY CHAIN</title>
	<!--<link rel="stylesheet" href="https://farm.myfarmdata.io/style.css" media="all" />-->
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
		integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
	<link rel="stylesheet" href="https://farm.myfarmdata.io/fontawesome/font-awesome.min.css" media="all" />
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/morris.js/0.5.1/morris.css">
	<script type="text/javascript" src="https://farm.myfarmdata.io/js/plugins/jquery/jquery.min.js"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
		integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous">
	</script>

	<script type="text/javascript" src="https://farm.myfarmdata.io/js/plugins/morris/raphael-min.js"></script>
	<script type="text/javascript" src="https://farm.myfarmdata.io/js/plugins/morris/morris.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

	<style>
		.width100 {
			width: 100%;
		}

		.width85 {
			width: 85%;
		}

		.width80 {
			width: 80%;
		}

		.width70 {
			width: 70%;
		}

		.width60 {
			width: 60%;
		}

		.width50 {
			width: 50%;
		}

		.width40 {
			width: 40%;
		}

		.width30 {
			width: 30%;
		}

		.width20 {
			width: 20%;
		}

		.width15 {
			width: 15%;
		}

		.textCenter {
			text-align: center;
		}

		.textBold {
			font-weight: bold;
		}

		.textGray {
			color: #969696;
		}

		.text12 {
			font-size: 12px;
		}

		.text14 {
			font-size: 14px;
		}

		.text16 {
			font-size: 16px;
		}

		.margin10 {
			margin: 5px 10px;
		}

		.margin20 {
			margin: 5px 20px;
		}

		.margin40 {
			margin: 5px 0px 0px 40px;
		}

		.marginTop30 {
			margin-top: 30px;
		}

		.backgroundGray {
			background: #f2f2f2;
		}

		.vAlign {
			vertical-align: top;
		}

		.borderRight {
			border-right: 1px solid #969696;
		}

		.borderBottom {
			border-bottom: 1px solid #969696;
		}

		.border {
			border: 1px solid #969696;
		}
	</style>
</head>

<body>
	<div class="container">
		<div class="col-md-12">
			<div>
				<img src="https://farm.myfarmdata.io/img/logo.jpg" alt="KAKAOMUNDO" width="90" height="90" style="float:right;">
            </div>
				<div>
					<h4 class="textCenter" style="padding-top:10px;">CERTIFICATE OF KAKAOMUNDO TRACEABILITY CHAIN</h4>
					<p class="textCenter">Pack Number : <b>KKM0037</b></p>
				</div>
			</div>
			<div class="col-md-12">
				<hr class="marginTop30 textGray" />
			</div>
			<div class="col-md-12">
				<p><b><u>Main product characteristic : </u></b></p>
				<table class="backgroundGray width100 ">
					<tr>
						<td class="vAlign width40">
							<p class="margin10">Description : </p>
						</td>
						<td class="vAlign width60">
							<p class="margin10 textGray">test1</p>
						</td>
					</tr>
					<tr>
						<td class="vAlign ">
							<p class="margin10">Unités : </p>
						</td>
						<td class="vAlign">
							<p class="margin10 textGray">Spanish/Espagnol</p>
						</td>
					</tr>
					<tr>
						<td class="vAlign ">
							<p class="margin10">Vendu à : </p>
						</td>
						<td class="vAlign">
							<p class="margin10 textGray">Roger Valere AYIMAMBENWE</p>
						</td>
					</tr>
				</table>
			</div>

			<div class="col-md-12">
				<p><b><u>Main tasks : </u></b></p>

				<div class="col-md-12" style="margin:5px;">
					<div class="col-md-12 border backgroundGray" style="padding:5px;">
						<b>Product transportation ( TRP0119 )</b></div>
					<div class="col-md-12 border" style="padding:5px;border-top:0px;">
						<p><b><u>Task Information :</u></b></p>
						<table class="backgroundGray width100">
							<tr>
								<td class="vAlign width40">
									<p class="margin10">Date de début prévue : </p>
								</td>
								<td class="vAlign width60">
									<p class="margin10 textGray">2022-06-16T11:28</p>
								</td>
							</tr>

						</table>
					</div>
					<div class="col-md-12 border" style="padding:5px;border-top:0px;">
						<p><b><u>Task Analytics :</u></b></p>
						<div class="col-md-12 " style="padding:5px;border-top:0px;">
							<div class="col-md-12" id="viewLocation_0">
								<div id="map_0"></div>
							</div>
							<div class="col-md-12" id="viewMedia_0"></div>
							<div class="col-md-12 row" id="viewGraph_0"></div>
						</div>
					</div>
				</div>

				<div class="col-md-12" style="margin:5px;">
					<div class="col-md-12 border backgroundGray" style="padding:5px;">
						<b>Fermentation fèves fraiches ( FERM0023 )</b></div>
					<div class="col-md-12 border" style="padding:5px;border-top:0px;">
						<p><b><u>Task Information :</u></b></p>
						<table class="backgroundGray width100">
							<tr>
								<td class="vAlign width40">
									<p class="margin10">Responsable : </p>
								</td>
								<td class="vAlign width60">
									<p class="margin10 textGray">Darshini M Shah</p>
								</td>
							</tr>
							<tr>
								<td class="vAlign width40">
									<p class="margin10">Equipe assignée : </p>
								</td>
								<td class="vAlign width60">
									<p class="margin10 textGray">DELEGATION CAISTAB LAMBARENE</p>
								</td>
							</tr>

						</table>
					</div>
					<div class="col-md-12 border" style="padding:5px;border-top:0px;">
						<p><b><u>Task Analytics :</u></b></p>
						<div class="col-md-12 " style="padding:5px;border-top:0px;">
							<div class="col-md-12" id="viewLocation_1">
								<div id="map_1"></div>
							</div>
							<div class="col-md-12" id="viewMedia_1"></div>
							<div class="col-md-12 row" id="viewGraph_1"></div>
						</div>
					</div>
				</div>

			</div>
		</div>

		<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA6j9juIKElTL81OCvMyTEY7C-p4lIA0_U">
		</script>
		<script type="text/javascript">
			var geocoder;
    var map;
    var address = "new york city";
    var field_person_img = null;
    
    function initMap() {
        // alert('hi');
        geocoder = new google.maps.Geocoder();
        
    }
   
    function codeAddress1(map_data,loopData) {
    
    if(Object.keys(map_data).length > 0){
        ${'$'}("#map_"+loopData).css("height",'500px');
    
        var contentString ='<div id="content"><div id="siteNotice"></div><h6 id="firstHeading" class="firstHeading">'+map_data.name+'</h6><div id="bodyContent">';
            if(map_data.field_contact != null){
                 contentString += "<img src='"+map_data.field_contact_img+"' width='100' height='100' title='"+map_data.field_contact+"'>";
            }
            contentString += "</div></div>";
            
        const infowindow = new google.maps.InfoWindow({
            content: contentString,
        });
        var latLng = {lat: +map_data.latitude, lng: +map_data.longitude};
        console.log(latLng);
        map = new google.maps.Map(document.getElementById('map_'+loopData), {
            zoom: 9,
            center: latLng
        });
        var marker = new google.maps.Marker({
            position: latLng,
            map: map
        });
        infowindow.open({
            anchor: marker,
            map,
            shouldFocus: false,
        });
        marker.addListener("click", () => {
            infowindow.open({
                anchor: marker,
                map,
                shouldFocus: false,
            });
        });
    }
}
   
${'$'}(function() {
    
    var loopData = 1;
       
        var res = {"task_obj":[{"name":"KKM0037","function":"CREATE_PACK","addedName":"paperbirdtech","formatted_date":"15\/June\/2022 04:49"},{"name":"D0021","function":"PACK","addedName":"paperbirdtech","formatted_date":"15\/June\/2022 06:14"},{"name":"D0022","function":"CREATE_PACK","addedName":"paperbirdtech","formatted_date":"15\/June\/2022 06:31"},{"name":"KKM0043","function":"PACK","addedName":"paperbirdtech","formatted_date":"28\/June\/2022 11:27"}],"packs":[{"id":228,"name":"KKM0037","charts":[{"id":7,"graph_type":"Bar chart","graph_name":"PBT Chart","graph_desc":"test descrp","graph_title":"PBT Chart","graph_abcissa_title":"Chart title","graph_ordinate_title":"Chart title 1_","lines":[{"id":10,"name":"In","line_type":"Result_Line","result_class":"TEMP_INSIDE","ref_ctrl_points":"N\/A","points":[]},{"id":11,"name":"Out","line_type":"Result_Line","result_class":"TEMP_OUTSIDE","ref_ctrl_points":"N\/A","points":[]},{"id":24,"name":"around","line_type":"Result_Line","result_class":"TEMP_AROUND","ref_ctrl_points":"N\/A","points":[]}]},{"id":8,"graph_type":"Pie chart","graph_name":"vidhee test","graph_desc":"vtest11","graph_title":"graph title","graph_abcissa_title":"graph atitle","graph_ordinate_title":"graph otiltle","lines":[]},{"id":9,"graph_type":"Line chart","graph_name":"Fermentation Curve","graph_desc":"Fermentation Temperature line chart","graph_title":"Fermentation curve","graph_abcissa_title":"Duration","graph_ordinate_title":"T\u00b0","lines":[{"id":14,"name":"Inside Temperature","line_type":"Result_Line","result_class":"TEMP_INSIDE","ref_ctrl_points":"N\/A","points":[]},{"id":15,"name":"OutsideTemperature","line_type":"Result_Line","result_class":"TEMP_OUTSIDE","ref_ctrl_points":"N\/A","points":[]},{"id":16,"name":"Reference Control Inside Temperature","line_type":"Ref_Control_Line","result_class":"N\/A","ref_ctrl_points":"0\/25 ; 12\/30 ; 24\/32 ; 36\/45 ; 48\/53 ; 60\/50 ; 72\/48 ; 84\/49 ; 96\/45 ; 108\/49 ; 120\/50","points":[{"id":"","pack_id":228,"value":"25 ","create_at":"","duration":"0"},{"id":"","pack_id":228,"value":"30 ","create_at":"","duration":" 12"},{"id":"","pack_id":228,"value":"32 ","create_at":"","duration":" 24"},{"id":"","pack_id":228,"value":"45 ","create_at":"","duration":" 36"},{"id":"","pack_id":228,"value":"53 ","create_at":"","duration":" 48"},{"id":"","pack_id":228,"value":"50 ","create_at":"","duration":" 60"},{"id":"","pack_id":228,"value":"48 ","create_at":"","duration":" 72"},{"id":"","pack_id":228,"value":"49 ","create_at":"","duration":" 84"},{"id":"","pack_id":228,"value":"45 ","create_at":"","duration":" 96"},{"id":"","pack_id":228,"value":"49 ","create_at":"","duration":" 108"},{"id":"","pack_id":228,"value":"50","create_at":"","duration":" 120"}]}]},{"id":11,"graph_type":"Line chart","graph_name":"new test JA","graph_desc":"Test","graph_title":"ja","graph_abcissa_title":"Date","graph_ordinate_title":"T\u00b0","lines":[{"id":21,"name":"Inside Temperature","line_type":"Result_Line","result_class":"TEMP_INSIDE","ref_ctrl_points":"N\/A","points":[]},{"id":22,"name":"outside Temperature","line_type":"Result_Line","result_class":"TEMP_OUTSIDE","ref_ctrl_points":"N\/A","points":[]},{"id":23,"name":"Control Temp Inside 1","line_type":"Ref_Control_Line","result_class":"N\/A","ref_ctrl_points":"1\/1 ; 2\/1.2 ; 2\/2 ; 3\/3 ; 3.5\/4 ; 4\/5 ; 5\/6 ; 5.8\/ 7","points":[{"id":"","pack_id":228,"value":"1 ","create_at":"","duration":"1"},{"id":"","pack_id":228,"value":"1.2 ","create_at":"","duration":" 2"},{"id":"","pack_id":228,"value":"2 ","create_at":"","duration":" 2"},{"id":"","pack_id":228,"value":"3 ","create_at":"","duration":" 3"},{"id":"","pack_id":228,"value":"4 ","create_at":"","duration":" 3.5"},{"id":"","pack_id":228,"value":"5 ","create_at":"","duration":" 4"},{"id":"","pack_id":228,"value":"6 ","create_at":"","duration":" 5"},{"id":"","pack_id":228,"value":" 7","create_at":"","duration":" 5.8"}]}]}]}],"pack_id":228,"task_id":403,"task_name":"TRP0119","name_pretty":"Product transportation","field_display":"129,130","error":"success","fields":[{"field_description":"Date de d\u00e9but pr\u00e9vue","field_value_org":"2022-06-16T11:28"}],"person_image":"","share_public":"","media_images":["https:\/\/farm.myfarmdata.io\/file_upload\/media\/1655292358_new1.png","https:\/\/farm.myfarmdata.io\/file_upload\/media\/1656137267_R.png"],"media_videos":["https:\/\/farm.myfarmdata.io\/file_upload\/media\/1655292338_big_buck_bunny_720p_1mb.mp4"],"field_map":true,"field_map_display":{"field_contact":null,"field_contact_img":null,"latitude":"4.9350646460266","longitude":"31.680699073242","name":"vtest"},"media_files":[{"id":220,"task_id":403,"name":"1655292338_big_buck_bunny_720p_1mb.mp4","type":null,"latitude":null,"longitude":null,"created_by":2,"created_date":"2022-06-15 16:55:38","deleted_at":null},{"id":221,"task_id":403,"name":"1655292358_new1.png","type":null,"latitude":null,"longitude":null,"created_by":2,"created_date":"2022-06-15 16:55:58","deleted_at":null},{"id":222,"task_id":403,"name":"1656137267_R.png","type":238,"latitude":null,"longitude":null,"created_by":2,"created_date":"2022-06-25 11:37:47","deleted_at":null}],"google_app_key":null}; 
        var htmlData = '';htmlData11 = '';htmlData2 = '';
                    
        //Media----------------Start
        if(res.media_images.length > 0){
            htmlData2 += '<div class="col-md-12">';
            htmlData2 += '<div class="panel panel-default" >';
            htmlData2 += '<div class="panel-heading"></hr><b>Media Images</b></div>';
            htmlData2 += '<div class="panel-body row">';
                ${'$'}.each(res.media_images, function(key, value){
                     htmlData2 += '<div class="col-md-6"><img src="'+value+'"  style="width:100%;height:300px;"></div>';
                });
                   
            htmlData2 += '</div>';
            htmlData2 += '</div>';
            htmlData2 += '</div>';
            ${'$'}('#viewMedia_'+loopData).html(htmlData2);
            
        }
        
        if(res.media_videos.length > 0){
            htmlData2 += '<div class="col-md-12">';
            htmlData2 += '<div class="panel panel-default" >';
            htmlData2 += '<div class="panel-heading"></hr><b>Media Videos</b></div>';
            htmlData2 += '<div class="panel-body">';
                ${'$'}.each(res.media_videos, function(key, value){
                     htmlData2 += '<div class="col-md-6"><video style="width:100%;height:300px;" controls src="'+value+'"></video></div>';
                });
                   
            htmlData2 += '</div>';
            htmlData2 += '</div>';
            htmlData2 += '</div>';
            ${'$'}('#viewMedia_'+loopData).html(htmlData2);
        }
        //Media----------------End
        // MAp ----------------Start
        if(res.packs.length > 0){
            let hasKey = res.hasOwnProperty('field_map_display');
            if(hasKey){
                // console.log(JSON.stringify(res.field_map_display));
                codeAddress1(res.field_map_display,loopData);
            }
            
        }
        // MAp ----------------End

                    
        if(res.packs.length > 0){
            ${'$'}.each(res.packs, function(kx, vx){
                var  graph_count = false;  
                htmlData += '<div class="col-md-12 row">';    
                
                ${'$'}.each(vx.charts, function(k, v){
                   
                    var  point_count = false; 
                    ${'$'}.each(v.lines, function(k2, v2) {
                        var points = v2.points;
                        if(points.length > 0){
                            point_count = true;
                            graph_count = true;
                        }                                
                    });
                                    
                    if(point_count == true){
                        htmlData += '<div class="col-md-6" style="padding:10px;">';
                        htmlData += '<div style="font-weight:bold;">'+v.graph_name+'</div>';
                        htmlData += '<div >';
                        if(v.graph_type == 'Radar chart'){
                            htmlData += '<canvas id="line'+loopData+'_'+vx.id+'_'+v.id+'" style="height: 300px;"></canvas>';
                        }else{
                            htmlData += '<div id="line'+loopData+'_'+vx.id+'_'+v.id+'" style="height: 300px;"></div>';
                        }
                                                    
                        htmlData += '<div id="legend'+loopData+'_'+vx.id+'_'+v.id+'" class="bars-legend"></div>';
                        htmlData += '</div>';
                        htmlData += '</div>';
                    }
                });
                
                if(graph_count == false){
                    htmlData += '<div class="col-md-12" style="text-align:center;">No Data Found</div>';
                }
                
                htmlData +='</div>';
                ${'$'}('#viewGraph_0').html(htmlData);
            });
                        
            ${'$'}.each(res.packs, function(kx, vx){
                ${'$'}.each(vx.charts, function(k, v){
                    var graph_ordinate_title = v.graph_ordinate_title;
                    var graph_abcissa_title = v.graph_abcissa_title;
                    var data_arr = [];
                        linename_arr = [];
                        ykeys_arr = [];
                        data_arr_radar = [];
                                    
                    var point_count = false;
                    var loop = 1;
                    var line_count = v.lines.length;
                    ${'$'}.each(v.lines, function(k2, v2) {
                        
                        var line_name = 'line '+loop;
                            ykeys_arr.push(line_name);
                            linename_arr.push(v2.name+' ('+graph_ordinate_title +')');
                                
                        var points = v2.points;
                        if(points.length > 0){
                            
                            ${'$'}.each(points, function(k3, v3) {
                                var j = 1;
                                var obj = {};
                                if(v3.duration != null){
                                    obj['y'] = (v3.duration).toString();
                                    ${'$'}.each(v.lines, function(k2, v2) {
                                        if(loop == j){
                                            obj['line '+j] = v3.value;
                                        }else{
                                            obj['line '+j] = null;
                                        }
                                        j++;
                                    });
                                    data_arr.push(obj); 
                                }
                                data_arr_radar.push(v3.value);
                            });
                            point_count = true;
                        }
                        loop++;
                    });
                    
                    var new_arr = [];
                    ${'$'}.each(data_arr, function(k, v) {
                        var index_value = -1;
                        var found = new_arr.some((el) => {
                            index_value = new_arr.indexOf(el);// -1
                            return el.y === v.y;
                        });
                        
                        if(found == false){
                            var obj = {};
                            ${'$'}.each(v, function(k1, v1) {
                                obj[k1] = v1;
                            });
                            new_arr.push(obj);
                        }else{
                            if(index_value != -1){
                                ${'$'}.each(new_arr[index_value], function(k1, v1) {
                                    if(v1 == null){
                                        new_arr[index_value][k1] = v[k1]; 
                                    }
                                });
                            }
                        }
                    });
                                                           
                    data_arr1 = new_arr.sort(function(a, b){
                        var a1= parseFloat(a.y), b1= parseFloat(b.y);
                        if(a1== b1) return 0;
                        return a1> b1? 1: -1;
                    });
                    
                    if(point_count == true){
                        var config = {
                            data: data_arr1,
                            xkey: 'y',
                            ykeys: ykeys_arr,
                            labels: linename_arr,
                            resize: false,
                            parseTime: false,
                            hoverCallback: function(index, options, content, row) {
                                var data = options.data[index]; 
                                var htmlData = '<div class="morris-hover-row-label">'+graph_abcissa_title+' : '+data.y+'</div>';
                                ${'$'}.each(options.ykeys, function(k, v) {
                                    if(data[v] != null){
                                        htmlData += '<div class="morris-hover-point">'+options.labels[k]+': '+data[v]+'</div>';
                                    }
                                });
                                return htmlData;
                            },
                        };
                        
                        if(v.graph_type == 'Line chart'){
                            config.element = 'line'+loopData+'_'+vx.id+'_'+v.id;
                            var browsersChart = Morris.Line(config);
                            browsersChart.options.labels.forEach(function(label, i) {
                            var legendItem = ${'$'}('<span></span>').text( label).prepend('<br><span>&nbsp;</span>');
                                legendItem.find('span')
                                    .css('backgroundColor', browsersChart.options.lineColors[i])
                                    .css('width', '10px')
                                    .css('height', '10px')
                                    .css('vertical-align', 'bottom')
                                    .css('display', 'inline-block')
                                    .css('margin', '3px');
                                    ${'$'}('#legend'+loopData+'_'+vx.id+'_'+v.id).append(legendItem);
                                });
                                        
                            }else if(v.graph_type == 'Bar chart'){
                                config.element = 'line'+loopData+'_'+vx.id+'_'+v.id;
                                config.stacked = true;
                                var browsersChart =  Morris.Bar(config);
                                browsersChart.options.labels.forEach(function(label, i) {
                                    var legendItem = ${'$'}('<span></span>').text( label).prepend('<br><span>&nbsp;</span>');
                                    legendItem.find('span')
                                        .css('backgroundColor', browsersChart.options.barColors[i])
                                        .css('width', '10px')
                                        .css('height', '10px')
                                        .css('vertical-align', 'bottom')
                                        .css('display', 'inline-block')
                                        .css('margin', '3px');
                                    ${'$'}('#legend'+loopData+'_'+vx.id+'_'+v.id).append(legendItem);
                                });
                            }else if(v.graph_type == 'Radar chart'){
                                var ctx = document.getElementById('line'+loopData+'_'+vx.id+'_'+v.id);
                                const data = {
                                    labels: linename_arr,
                                    datasets: [{
                                        label: 'Value',
                                        data: data_arr_radar,
                                        fill: true,
                                        backgroundColor: 'rgba(255, 99, 132, 0.2)',
                                        borderColor: 'rgb(255, 99, 132)',
                                        pointBackgroundColor: 'rgb(255, 99, 132)',
                                        pointBorderColor: '#fff',
                                        pointHoverBackgroundColor: '#fff',
                                        pointHoverBorderColor: 'rgb(255, 99, 132)'
                                    }]
                                };
                                const config = {
                                    type: 'radar',
                                    data: data,
                                    options: {
                                        elements: {
                                            line: {
                                                borderWidth: 3
                                            }
                                        }
                                    },
                                };
                                var myChart = new Chart(ctx, config);
                            }
                                    
                    }    
                            
                            
                    
                            
                });
            });
        }else{
            htmlData = '';
            ${'$'}('#viewGraph_0').html(htmlData);
        }
    loopData++;                
                    
       
        var res = {"task_obj":[{"name":"KKM0037","function":"PACK","addedName":"paperbirdtech","formatted_date":"28\/June\/2022 11:26"},{"name":"KKM0043","function":"CREATE_PACK","addedName":"paperbirdtech","formatted_date":"28\/June\/2022 11:26"},{"name":"Claude Obiang","function":"PERSON","addedName":"paperbirdtech","formatted_date":"30\/June\/2022 11:47"}],"packs":[{"id":228,"name":"KKM0037","charts":[{"id":11,"graph_type":"Line chart","graph_name":"new test JA","graph_desc":"Test","graph_title":"ja","graph_abcissa_title":"Date","graph_ordinate_title":"T\u00b0","lines":[{"id":21,"name":"Inside Temperature","line_type":"Result_Line","result_class":"TEMP_INSIDE","ref_ctrl_points":"N\/A","points":[]},{"id":22,"name":"outside Temperature","line_type":"Result_Line","result_class":"TEMP_OUTSIDE","ref_ctrl_points":"N\/A","points":[]},{"id":23,"name":"Control Temp Inside 1","line_type":"Ref_Control_Line","result_class":"N\/A","ref_ctrl_points":"1\/1 ; 2\/1.2 ; 2\/2 ; 3\/3 ; 3.5\/4 ; 4\/5 ; 5\/6 ; 5.8\/ 7","points":[{"id":"","pack_id":228,"value":"1 ","create_at":"","duration":"1"},{"id":"","pack_id":228,"value":"1.2 ","create_at":"","duration":" 2"},{"id":"","pack_id":228,"value":"2 ","create_at":"","duration":" 2"},{"id":"","pack_id":228,"value":"3 ","create_at":"","duration":" 3"},{"id":"","pack_id":228,"value":"4 ","create_at":"","duration":" 3.5"},{"id":"","pack_id":228,"value":"5 ","create_at":"","duration":" 4"},{"id":"","pack_id":228,"value":"6 ","create_at":"","duration":" 5"},{"id":"","pack_id":228,"value":" 7","create_at":"","duration":" 5.8"}]}]}]}],"pack_id":228,"task_id":405,"task_name":"FERM0023","name_pretty":"Fermentation f\u00e8ves fraiches","field_display":"133,137,138","error":"success","fields":[{"field_description":"Responsable","field_value_org":"Darshini M Shah"},{"field_description":"Equipe assign\u00e9e","field_value_org":"DELEGATION CAISTAB LAMBARENE"}],"person_image":"","share_public":"","media_images":[],"media_videos":[],"field_map":false,"media_files":[],"google_app_key":null}; 
        var htmlData = '';htmlData11 = '';htmlData2 = '';
                    
        //Media----------------Start
        if(res.media_images.length > 0){
            htmlData2 += '<div class="col-md-12">';
            htmlData2 += '<div class="panel panel-default" >';
            htmlData2 += '<div class="panel-heading"></hr><b>Media Images</b></div>';
            htmlData2 += '<div class="panel-body row">';
                ${'$'}.each(res.media_images, function(key, value){
                     htmlData2 += '<div class="col-md-6"><img src="'+value+'"  style="width:100%;height:300px;"></div>';
                });
                   
            htmlData2 += '</div>';
            htmlData2 += '</div>';
            htmlData2 += '</div>';
            ${'$'}('#viewMedia_'+loopData).html(htmlData2);
            
        }
        
        if(res.media_videos.length > 0){
            htmlData2 += '<div class="col-md-12">';
            htmlData2 += '<div class="panel panel-default" >';
            htmlData2 += '<div class="panel-heading"></hr><b>Media Videos</b></div>';
            htmlData2 += '<div class="panel-body">';
                ${'$'}.each(res.media_videos, function(key, value){
                     htmlData2 += '<div class="col-md-6"><video style="width:100%;height:300px;" controls src="'+value+'"></video></div>';
                });
                   
            htmlData2 += '</div>';
            htmlData2 += '</div>';
            htmlData2 += '</div>';
            ${'$'}('#viewMedia_'+loopData).html(htmlData2);
        }
        //Media----------------End
        // MAp ----------------Start
        if(res.packs.length > 0){
            let hasKey = res.hasOwnProperty('field_map_display');
            if(hasKey){
                // console.log(JSON.stringify(res.field_map_display));
                codeAddress1(res.field_map_display,loopData);
            }
            
        }
        // MAp ----------------End

                    
        if(res.packs.length > 0){
            ${'$'}.each(res.packs, function(kx, vx){
                var  graph_count = false;  
                htmlData += '<div class="col-md-12 row">';    
                
                ${'$'}.each(vx.charts, function(k, v){
                   
                    var  point_count = false; 
                    ${'$'}.each(v.lines, function(k2, v2) {
                        var points = v2.points;
                        if(points.length > 0){
                            point_count = true;
                            graph_count = true;
                        }                                
                    });
                                    
                    if(point_count == true){
                        htmlData += '<div class="col-md-6" style="padding:10px;">';
                        htmlData += '<div style="font-weight:bold;">'+v.graph_name+'</div>';
                        htmlData += '<div >';
                        if(v.graph_type == 'Radar chart'){
                            htmlData += '<canvas id="line'+loopData+'_'+vx.id+'_'+v.id+'" style="height: 300px;"></canvas>';
                        }else{
                            htmlData += '<div id="line'+loopData+'_'+vx.id+'_'+v.id+'" style="height: 300px;"></div>';
                        }
                                                    
                        htmlData += '<div id="legend'+loopData+'_'+vx.id+'_'+v.id+'" class="bars-legend"></div>';
                        htmlData += '</div>';
                        htmlData += '</div>';
                    }
                });
                
                if(graph_count == false){
                    htmlData += '<div class="col-md-12" style="text-align:center;">No Data Found</div>';
                }
                
                htmlData +='</div>';
                ${'$'}('#viewGraph_1').html(htmlData);
            });
                        
            ${'$'}.each(res.packs, function(kx, vx){
                ${'$'}.each(vx.charts, function(k, v){
                    var graph_ordinate_title = v.graph_ordinate_title;
                    var graph_abcissa_title = v.graph_abcissa_title;
                    var data_arr = [];
                        linename_arr = [];
                        ykeys_arr = [];
                        data_arr_radar = [];
                                    
                    var point_count = false;
                    var loop = 1;
                    var line_count = v.lines.length;
                    ${'$'}.each(v.lines, function(k2, v2) {
                        
                        var line_name = 'line '+loop;
                            ykeys_arr.push(line_name);
                            linename_arr.push(v2.name+' ('+graph_ordinate_title +')');
                                
                        var points = v2.points;
                        if(points.length > 0){
                            
                            ${'$'}.each(points, function(k3, v3) {
                                var j = 1;
                                var obj = {};
                                if(v3.duration != null){
                                    obj['y'] = (v3.duration).toString();
                                    ${'$'}.each(v.lines, function(k2, v2) {
                                        if(loop == j){
                                            obj['line '+j] = v3.value;
                                        }else{
                                            obj['line '+j] = null;
                                        }
                                        j++;
                                    });
                                    data_arr.push(obj); 
                                }
                                data_arr_radar.push(v3.value);
                            });
                            point_count = true;
                        }
                        loop++;
                    });
                    
                    var new_arr = [];
                    ${'$'}.each(data_arr, function(k, v) {
                        var index_value = -1;
                        var found = new_arr.some((el) => {
                            index_value = new_arr.indexOf(el);// -1
                            return el.y === v.y;
                        });
                        
                        if(found == false){
                            var obj = {};
                            ${'$'}.each(v, function(k1, v1) {
                                obj[k1] = v1;
                            });
                            new_arr.push(obj);
                        }else{
                            if(index_value != -1){
                                ${'$'}.each(new_arr[index_value], function(k1, v1) {
                                    if(v1 == null){
                                        new_arr[index_value][k1] = v[k1]; 
                                    }
                                });
                            }
                        }
                    });
                                                           
                    data_arr1 = new_arr.sort(function(a, b){
                        var a1= parseFloat(a.y), b1= parseFloat(b.y);
                        if(a1== b1) return 0;
                        return a1> b1? 1: -1;
                    });
                    
                    if(point_count == true){
                        var config = {
                            data: data_arr1,
                            xkey: 'y',
                            ykeys: ykeys_arr,
                            labels: linename_arr,
                            resize: false,
                            parseTime: false,
                            hoverCallback: function(index, options, content, row) {
                                var data = options.data[index]; 
                                var htmlData = '<div class="morris-hover-row-label">'+graph_abcissa_title+' : '+data.y+'</div>';
                                ${'$'}.each(options.ykeys, function(k, v) {
                                    if(data[v] != null){
                                        htmlData += '<div class="morris-hover-point">'+options.labels[k]+': '+data[v]+'</div>';
                                    }
                                });
                                return htmlData;
                            },
                        };
                        
                        if(v.graph_type == 'Line chart'){
                            config.element = 'line'+loopData+'_'+vx.id+'_'+v.id;
                            var browsersChart = Morris.Line(config);
                            browsersChart.options.labels.forEach(function(label, i) {
                            var legendItem = ${'$'}('<span></span>').text( label).prepend('<br><span>&nbsp;</span>');
                                legendItem.find('span')
                                    .css('backgroundColor', browsersChart.options.lineColors[i])
                                    .css('width', '10px')
                                    .css('height', '10px')
                                    .css('vertical-align', 'bottom')
                                    .css('display', 'inline-block')
                                    .css('margin', '3px');
                                    ${'$'}('#legend'+loopData+'_'+vx.id+'_'+v.id).append(legendItem);
                                });
                                        
                            }else if(v.graph_type == 'Bar chart'){
                                config.element = 'line'+loopData+'_'+vx.id+'_'+v.id;
                                config.stacked = true;
                                var browsersChart =  Morris.Bar(config);
                                browsersChart.options.labels.forEach(function(label, i) {
                                    var legendItem = ${'$'}('<span></span>').text( label).prepend('<br><span>&nbsp;</span>');
                                    legendItem.find('span')
                                        .css('backgroundColor', browsersChart.options.barColors[i])
                                        .css('width', '10px')
                                        .css('height', '10px')
                                        .css('vertical-align', 'bottom')
                                        .css('display', 'inline-block')
                                        .css('margin', '3px');
                                    ${'$'}('#legend'+loopData+'_'+vx.id+'_'+v.id).append(legendItem);
                                });
                            }else if(v.graph_type == 'Radar chart'){
                                var ctx = document.getElementById('line'+loopData+'_'+vx.id+'_'+v.id);
                                const data = {
                                    labels: linename_arr,
                                    datasets: [{
                                        label: 'Value',
                                        data: data_arr_radar,
                                        fill: true,
                                        backgroundColor: 'rgba(255, 99, 132, 0.2)',
                                        borderColor: 'rgb(255, 99, 132)',
                                        pointBackgroundColor: 'rgb(255, 99, 132)',
                                        pointBorderColor: '#fff',
                                        pointHoverBackgroundColor: '#fff',
                                        pointHoverBorderColor: 'rgb(255, 99, 132)'
                                    }]
                                };
                                const config = {
                                    type: 'radar',
                                    data: data,
                                    options: {
                                        elements: {
                                            line: {
                                                borderWidth: 3
                                            }
                                        }
                                    },
                                };
                                var myChart = new Chart(ctx, config);
                            }
                                    
                    }    
                            
                            
                    
                            
                });
            });
        }else{
            htmlData = '';
            ${'$'}('#viewGraph_1').html(htmlData);
        }
    loopData++;                
                    
     
});

		</script>
</body>

</html>
        """.trimIndent()

            companion object {
            const val STORAGE_PERMISSION_REQUEST = 1
        }

    override fun onResponse(
        call: Call<ResponsefieldClasses>,
        response: Response<ResponsefieldClasses>
    ) {
        if (response.isSuccessful){
            myHtmlContext=response.body().toString()
            AppUtils.logDebug(TAG,"myHtmlContext"+myHtmlContext)
        }
    }

    override fun onFailure(call: Call<ResponsefieldClasses>, t: Throwable) {
    }
}

