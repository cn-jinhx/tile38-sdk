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

### GeoJson实体
由于实际使用过程中需要大量依赖GeoJson实体对象，为此当前该库内置了对应的GeoJson实体对象便于
进行数据的序列化与反序列化，当然也可以用于其他需要这类实体对象的场景下而不仅仅作为tile38的SDK
使用，具体的使用方式介绍如下。  

* 在你已知所需要反序列化的类型情况下，可以采用以下方式进行反序列化。  
```java
FeatureCollection featureCollection = new ObjectMapper().readValue(inputStream, FeatureCollection.class);
```

* 如果对具体类型未知或期望使用更为通用的类型进行转换，可以采用以下方式进行反序列化。  
```java
GeoJsonObject object = new ObjectMapper().readValue(inputStream, GeoJsonObject.class);
if (object instanceof Polygon) {
	...
} else if (object instanceof Feature) {
	...
}
```
* 序列化GeoJson对象  
相较于反序列化，序列化很容易只要实例化所需的类型并进行填充即可，最终使用Jackson`ObjectMapper`
进行转换即可。  

```java
FeatureCollection featureCollection = new FeatureCollection();
featureCollection.add(new Feature());

String json= new ObjectMapper().writeValueAsString(featureCollection);
```

## 地理围栏

### 观察行为
通过针对传统地理围栏的观察我们可以发现大多数地理围栏主要具备以下几种行为其中的一种，并且业务应用可根据
这类触发的行为进行有效的逻辑处理。  
* inside: 当对象处在指定的区域范围内时  
* outside: 当对象处在指定的区域范围外时
* enter: 当对象未处在指定的范围内时，对象进入区域内时触发
* exit: 当对象处在指定的范围内，离开指定的区域时触发
* cross: 当对象未处在指定的区域范围内，对象进入并离开区域时触发

在tile38中使用下述指令方式控制所需关注的行为。  
```
SETCHAN warehouse NEARBY fleet FENCE DETECT inside,outside POINT 33.462 -112.268 6000
```

### 检索方式
为了有效的使用各类条件关键词，为此需要针对核心的关键词进行介绍讲解，以便更好的了解并根据实际项目需要进行取舍
从而提高针对项目的符合度，当前tile38主要提供了`NEARBY`、`INTERSECTS`与`WITHIN`关键词用于进行检索，针对
各类关键词的检索方式介绍如下。  

#### NEARBY
该指令用于在指定集合中搜索最接近的点，其内部使用KNN算法代替标准的overlap+Haversine算法，将结果参照就近原则
进行排序输出。

### 条件筛选

#### WHERE
基本常用的条件筛选，可用来针对Field或者GeoJson对象中的`properties`属性进行筛选，如下述针对Field进行
过滤筛选实现数据的过滤。  

```
SET fleet truck1  FIELD name Andy  POINT 33 -112
SET fleet truck4  FIELD info '{"speed":60,"age":21,"name":"Tom"}'  POINT 33 -112

INTERSECTS fleet WHERE 'name == "Andy"' BOUNDS 30 -120 40 -100
INTERSECTS fleet WHERE 'info.speed > 45 && info.age < 21' BOUNDS 30 -120 40 -100
```

除了以上根据Field的过滤方式，也可以通过对GetJson对象中属性进行过滤，比如下述设置的GeoJson对象进行
条件筛选。

```
SET fleet truck5 OBJECT '{"type":"Feature","geometry":{"type":"Point","coordinates":[-112,33]},"properties":{"speed":55}}'
```

设置完以上对象后，我们就可以针对其中的`properties`成员的属性进行条件筛选，从而实现我们所需要的功能。

```
INTERSECTS fleet WHERE 'properties.name == "Carol"' BOUNDS 30 -120 40 -100
```

#### WHEREIN
该条件筛选方式类似我们SQL中的in关键字，用来约束条件字段的值在给定的列表中包含，其使用方式如下方式。

```
INTERSECTS fleet WHEREIN doors 2 2 5 WHEREIN wheels 3 14 18 22 BOUNDS 30 -120 40 -100
```

#### WHEREEVAL
与where一致，均是用于进行条件过滤筛选，只是其主要采用Lua脚本进行条件的计算，从而判定条件是否满足当前
对应的表达式，关于表达式的更多使用方式可以[参考此](https://tile38.com/commands/eval)，下述将主
要列举其大致的使用方式。  

```
NEARBY fleet WHEREEVAL "return FIELDS.wheels > ARGV[1] or (FIELDS.length * FIELDS.width) 
> ARGV[2]" 2 8 120 POINT 33.462 -112.268 6000
```

#### MATCH
其与where的区别就是其匹配的非字段，而是数据本身的名称，比如在集合a中检索数据id为c开头的，就可以使用
该方式进行检索，下面给出一个简单的示例以供参考。  

```
NEARBY fleet MATCH truck* POINT 33.462 -112.268 6000
```

#### CLIP
CLIP 告诉服务器通过搜索的边界框区域裁剪相交的对象。它只能与这些区域格式一起使用：BOUNDS、TILE、
QUADKEY、HASH。
