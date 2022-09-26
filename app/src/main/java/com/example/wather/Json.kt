package com.example.wather

import org.w3c.dom.ls.LSInput
import java.io.Serializable

data class Json(

    var publishingOffice: String?,
    var reportDatetime: String?,
    //var timeSeries: TimeSeries?
)

data class TimeSeries(
    var timeDefines: List<String>?,
    var areas: Areas?
)

data class Areas(
    var area: Area?,
    var weatherCodes: List<String>?,
    var weathers: List<String>?,
    var winds: List<String>?,
    var waves: List<String>?
)

data class Area(
    var name: String?,
    var code: String?
)