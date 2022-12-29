# tile38-sdk
用于提供访问tile38的客户端SDK  

## 基础知识

### 坐标体系
我们所熟知的大部分都是经纬度坐标体系，当然我们所使用的地图的实际加载都是基于对应的投影坐标系从而
实现基于瓦片的加载，为了标准化这类坐标体系，除了我们所熟知的WGS84、BeiJing1954与CGCS2000等
坐标体系，还存在一个统一了所有坐标体系的组织EPSP（European Petroleum Survey Group，中文
名称为欧洲石油调查组织）为所有的坐标体系规范了对应的编码，比如我们常用的WGS84对应的EPSP编码为
4326，根据实际的项目需要我们往往需要需要将不同体系的坐标进行转换，如根据WGS84经纬度体系转换为
对应的WGS84 Web墨卡投影坐标系（EPSG3857）。  
我们可以直接通过[epsg.io](https://epsg.io/)官网进行在线的转换，对应的我们也可以通过以下各类
语言类库进行转换。  
- [Python语言](https://gist.github.com/maptiler/fddb5ce33ba995d5523de9afdf8ef118)
- [Java语言](https://github.com/scaleset/scaleset-geo/blob/master/src/main/java/com/scaleset/geo/math/GoogleMapsTileMath.java)
- [JavaScript语言](https://github.com/datalyze-solutions/globalmaptiles/blob/master/globalmaptiles.js)


### 对象类型  
在使用tile38中我们可以使用很多类型的位置信息，为此我们需要了解我们可以使用哪些类型的对象数据，从而能够
根据项目的需要灵活选择，而不只是仅仅只会使用基本的经纬度来满足所有的需求。  

#### 经纬度点
最基本的对象类型由纬度和经度组成的点，并且还有一个可选的z成员用于辅助，可存储海拔或时间戳。
```
SET fleet truck1 POINT 33.5123 -112.2693
SET fleet truck1 POINT 33.5123 -112.2693 225
```

#### 边界框
边界框由两个点组成，第一个是最西南点，第二个是最东北点。  
```
SET fleet truck1 BOUNDS 30.3213 -110.2321 40.2132 -100.3221
```

#### GeoHash
其就是利用字符串表示一个坐标点，字符串的长度意味着坐标的精度，关于GeoHash的更多基本知识的介绍
可以参考[本文章](https://www.jianshu.com/p/1ecf03293b9a)。
```
SET fleet truck1 HASH 9tbnthxzr
```

#### GeoJson
GeoJSON是一种对各种地理数据结构进行编码的格式，基于Javascript对象表示法的地理空间信息数据
交换格式。GeoJSON支持下面几何类型：点、线、面、多点、多线、多面和几何集合。GeoJSON里的特征
包含一个几何对象和其他属性，特征集合表示一系列特征。可以通过
[此网站获取GeoJson的表达](https://geojson.io/)

* 1. Point
代表一个坐标点。
```
SET city tempe OBJECT '{"type":"Point","coordinates": [-111.1232, 30.2313]}'
```

* 2. MultiPoint
代表多表坐标点数组
```
SET city tempe OBJECT '{"type":"MultiPoint","coordinates": [[-111.1232, 30.2313],[-110.3213, 31.3213]]}'
```

* 3. LineString
代表线条，最少需要提供一个起点和终点坐标。
```
SET city tempe OBJECT '{"type":"LineString","coordinates": [[-111.1232, 30.2313],[-110.3213, 31.3213]]}'
```

* 4. MultiLineString
代表多个线条的组合。
```
SET city tempe OBJECT '{"type":"MultiLineString",
    "coordinates": [ 
        [[10, 10], [20, 20], [10, 40]], 
        [[40, 40], [30, 30], [40, 20], [30, 10]] 
    ] 
}'
```

* 5. Polygon
代表多边形，对于“多边形”，“坐标”成员必须是线性环坐标数组的数组。线性闭环（LinearRing）具有四个或更多位置的封闭 LineString。
“Closed”仅仅意味着LinearRing的起点和终点必须在同一个位置。
```
SET city tempe OBJECT '
    { "type": "Polygon", 
        "coordinates": [
            [[35, 10], [45, 45], [15, 40], [10, 20], [35, 10]], 
            [[20, 30], [35, 35], [30, 20], [20, 30]]
        ]
    }
'
```

* 6. MultiPolygon
代表多个多边形组合。
```
SET city tempe OBJECT '
{ "type": "MultiPolygon", 
   "coordinates": [
        [
            [[30, 20], [45, 40], [10, 40], [30, 20]]
        ], 
        [
            [[15, 5], [40, 10], [10, 20], [5, 10], [15, 5]]
        ]
   ]
}'
```

* 7. GeometryCollection
上面看到的其他几何类型的异构组合。GeometryCollection 对象没有“coordinates”成员，而
是有一个名为“geometries”的成员。“geometries”的值是一个数组。该数组的每个元素都是一个 
GeoJSON Geometry 对象。
```
SET city tempe OBJECT '
{ "type": "GeometryCollection", 
    "geometries": [
      {
        "type": "Point",
        "coordinates": [0, 0]
      }, {
        "type": "Polygon",
        "coordinates": [[[45, 45], [45, -45], [-45, -45], [-45, 45], [45,45]]]
       }
    ]
}
'
```

* 8. Feature
在上一节中看到的几何形状定义了可以在地图上绘制的形状。然而，我们在地图上的形状也应该有一些现实
世界的意义。此含义由该形状的属性定义。
```
SET city tempe OBJECT '
{"type": "Feature",
      "geometry": {
        "type": "Point",
        "coordinates": [-80.83775386582222, 35.24980190252168]
      },
      "properties": {
        "name": "DOUBLE OAKS CENTER",
        "address": "1326 WOODWARD AV"
      }
}
'
```
对应的`FeatureCollection`顾名思义就是对应多个Feature数组组合。

#### XYZ栅格瓦片
目前我们所见的所有地图底图服务都是瓦片地图的方式发布的。瓦片地图金字塔模型是一种多分辨率层次模
型，从瓦片金字塔的底层到顶层，分辨率越来越低，但表示的地理范围不变。 当我们建立好了影像金字塔
后，前端再请求地图时，则将只是在切好的瓦片缓存中，找到对应级别里对应的瓦片即可。然后在前端将
这些请求到的瓦片拼接出来，便可以得到用户需要的级别下的可视范围内的瓦片了。  

参考文档:
- [栅格瓦片在线选取](https://www.maptiler.com/google-maps-coordinates-tile-bounds-projection/#3/15.00/50.00)  
- [栅格瓦片图层](https://www.wenjiangs.com/doc/pupv4ij7anvo)  
- [墨卡托投影与瓦片地图](https://blog.csdn.net/weixin_42582542/article/details/121080586)  

瓦片本身并不是用于进行存储，瓦片的支持主要用于便于根据瓦片进行查询，便于查询特定瓦片范围内的坐标
点数据，比如以下两种查询方式中就可以使用瓦片进行查询，从而便于大屏等这类数据基于用户缩放级别以及
当前所显示的瓦片进行坐标点的检索与呈现。  

```
INTERSECTS fleet TILE 16 10 5
#或
WITHIN fleet TILE 16 10 5
```

#### 扇形
顾名思义，就是通过给定点查询制定扇形范围内的坐标点数据，比如如下就是查询给定坐标点0到90度1000米
范围内所有符合的坐标点。
```
WITHIN fleet SECTOR 33.5123 -112.2693 1000 0 90
```