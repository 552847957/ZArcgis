/*
 * *********************************************************
 *   author   colin
 *   company  telchina
 *   email    wanglin2046@126.com
 *   date     19-1-23 下午1:50
 * ********************************************************
 */

package com.telchina.tharcgiscore.layermgr;

import android.graphics.Color;

import com.esri.arcgisruntime.symbology.SimpleFillSymbol;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.telchina.tharcgiscore.R;

/**
 * ArcGis地图绘画缓冲区属性设置
 */
public class GraphicsOverlayConfig {

    /**
     * 点图标的设置,图片的优先级高于纯色点
     */
    private int pointPic       = R.drawable.ic_gismap_point_blue;//点图标
    private int pointPicWidth  = 60;//点图表的宽度
    private int pointPicHeight = 60;//点图表的高度

    /**
     * 纯色点的设置
     */
    private int pointColor;//点颜色
    private SimpleMarkerSymbol.Style pointType = SimpleMarkerSymbol.Style.CIRCLE;//点的形状， 默认是圆点
    private int                      pointSize = 25;

    /**
     * 线设置
     */
    private int                    lineColor = Color.argb(90, 255, 0, 0);//线的颜色
    private int                    lineWidth = 4;//线宽度
    private SimpleLineSymbol.Style lineType  = SimpleLineSymbol.Style.SOLID;//线的类型，默认是实心线

    /**
     * 面设置
     */
    private int                    polygonLineColor = Color.argb(90, 0, 191, 225);//面的边线的颜色
    private int                    polygonLineWidth = 2;//面的边线宽度
    private SimpleLineSymbol.Style polygonLineType  = SimpleLineSymbol.Style.SOLID;//面的边线的类型，默认是实心线
    private int                    polygonFillColor = Color.argb(90, 0, 191, 225);//面的填充的颜色
    private SimpleFillSymbol.Style polygonFillType  = SimpleFillSymbol.Style.SOLID;//填充面的类型，默认实心面

    public static GraphicsOverlayConfig instanceBuffer() {
        return new GraphicsOverlayConfig();
    }

    public static GraphicsOverlayConfig instanceHighlight() {
        GraphicsOverlayConfig overlayConfig = new GraphicsOverlayConfig();
        overlayConfig.pointColor = Color.RED;//点颜色
        overlayConfig.pointPic = R.drawable.ic_gismap_point_red;//点图标
        overlayConfig.lineColor = Color.argb(255, 65, 105, 225);//线的颜色
        overlayConfig.lineWidth = 6;//线宽度
        overlayConfig.polygonLineColor = Color.argb(255, 65, 105, 225);//面的边线的颜色
        overlayConfig.polygonFillColor = Color.argb(90, 0, 191, 225);//面的填充的颜色
        return overlayConfig;
    }
    
    public static GraphicsOverlayConfig instanceDraw() {
        GraphicsOverlayConfig overlayConfig = new GraphicsOverlayConfig();
        overlayConfig.pointColor = Color.argb(90, 255, 0, 0);//点颜色
        overlayConfig.pointPic = R.drawable.ic_gismap_point_blue;//点图标
        overlayConfig.lineColor = Color.argb(90, 255, 0, 0);//线的颜色
        overlayConfig.lineWidth = 4;//线宽度
        overlayConfig.polygonLineColor = Color.RED;//面的边线的颜色
        overlayConfig.polygonFillColor = Color.argb(90, 255, 0, 0);//面的填充的颜色
        return overlayConfig;
    }

    public GraphicsOverlayConfig setPointColor(int pointColor) {
        this.pointColor = pointColor;
        return this;
    }

    public GraphicsOverlayConfig setPointType(SimpleMarkerSymbol.Style pointType) {
        this.pointType = pointType;
        return this;
    }

    public GraphicsOverlayConfig setPointSize(int pointSize) {
        this.pointSize = pointSize;
        return this;
    }

    public GraphicsOverlayConfig setPointPic(int pointPic) {
        this.pointPic = pointPic;
        return this;
    }

    public GraphicsOverlayConfig setPointPicWidth(int pointPicWidth) {
        this.pointPicWidth = pointPicWidth;
        return this;
    }

    public GraphicsOverlayConfig setPointPicHeight(int pointPicHeight) {
        this.pointPicHeight = pointPicHeight;
        return this;
    }

    public GraphicsOverlayConfig setLineColor(int lineColor) {
        this.lineColor = lineColor;
        return this;
    }

    public GraphicsOverlayConfig setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
        return this;
    }

    public GraphicsOverlayConfig setLineType(SimpleLineSymbol.Style lineType) {
        this.lineType = lineType;
        return this;
    }

    public GraphicsOverlayConfig setPolygonLineColor(int polygonLineColor) {
        this.polygonLineColor = polygonLineColor;
        return this;
    }

    public GraphicsOverlayConfig setPolygonLineWidth(int polygonLineWidth) {
        this.polygonLineWidth = polygonLineWidth;
        return this;
    }

    public GraphicsOverlayConfig setPolygonLineType(SimpleLineSymbol.Style polygonLineType) {
        this.polygonLineType = polygonLineType;
        return this;
    }

    public GraphicsOverlayConfig setPolygonFillColor(int polygonFillColor) {
        this.polygonFillColor = polygonFillColor;
        return this;
    }

    public GraphicsOverlayConfig setPolygonFillType(SimpleFillSymbol.Style polygonFillType) {
        this.polygonFillType = polygonFillType;
        return this;
    }

    public int getPointPic() {
        return pointPic;
    }

    public int getPointPicWidth() {
        return pointPicWidth;
    }

    public int getPointPicHeight() {
        return pointPicHeight;
    }

    public int getPointColor() {
        return pointColor;
    }

    public SimpleMarkerSymbol.Style getPointType() {
        return pointType;
    }

    public int getPointSize() {
        return pointSize;
    }

    public int getLineColor() {
        return lineColor;
    }

    public int getLineWidth() {
        return lineWidth;
    }

    public SimpleLineSymbol.Style getLineType() {
        return lineType;
    }

    public int getPolygonLineColor() {
        return polygonLineColor;
    }

    public int getPolygonLineWidth() {
        return polygonLineWidth;
    }

    public SimpleLineSymbol.Style getPolygonLineType() {
        return polygonLineType;
    }

    public int getPolygonFillColor() {
        return polygonFillColor;
    }

    public SimpleFillSymbol.Style getPolygonFillType() {
        return polygonFillType;
    }

}
