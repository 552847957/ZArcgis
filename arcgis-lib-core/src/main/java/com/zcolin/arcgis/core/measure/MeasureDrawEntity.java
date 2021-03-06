/*
 * *********************************************************
 *   author   colin
 *   email    wanglin2046@126.com
 *   date     19-1-30 下午1:33
 * ********************************************************
 */

package com.zcolin.arcgis.core.measure;

import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;

import java.util.List;

public class MeasureDrawEntity {
    private List<GraphicsOverlay> textGraphic    =null;//文字集合
    private List<GraphicsOverlay> polygonGraphic =null;//面集合
    private List<GraphicsOverlay> lineGraphic    =null;//线集合
    private List<GraphicsOverlay> pointGraphic   =null;//点集合
    private List<List<Point>>     pointGroup     =null;//绘制点的集合的集合

    public MeasureDrawEntity(List<GraphicsOverlay> textGraphic, List<GraphicsOverlay> polygonGraphic, List<GraphicsOverlay> lineGraphic, List<GraphicsOverlay> pointGraphic, List<List<Point>> pointGroup) {
        this.textGraphic = textGraphic;
        this.polygonGraphic = polygonGraphic;
        this.lineGraphic = lineGraphic;
        this.pointGraphic = pointGraphic;
        this.pointGroup = pointGroup;
    }
    public MeasureDrawEntity(){

    }

    public List<GraphicsOverlay> getTextGraphic() {
        return textGraphic;
    }

    public void setTextGraphic(List<GraphicsOverlay> textGraphic) {
        this.textGraphic = textGraphic;
    }

    public List<GraphicsOverlay> getPolygonGraphic() {
        return polygonGraphic;
    }

    public void setPolygonGraphic(List<GraphicsOverlay> polygonGraphic) {
        this.polygonGraphic = polygonGraphic;
    }

    public List<GraphicsOverlay> getLineGraphic() {
        return lineGraphic;
    }

    public void setLineGraphic(List<GraphicsOverlay> lineGraphic) {
        this.lineGraphic = lineGraphic;
    }

    public List<GraphicsOverlay> getPointGraphic() {
        return pointGraphic;
    }

    public void setPointGraphic(List<GraphicsOverlay> pointGraphic) {
        this.pointGraphic = pointGraphic;
    }

    public List<List<Point>> getPointGroup() {
        return pointGroup;
    }

    public void setPointGroup(List<List<Point>> pointGroup) {
        this.pointGroup = pointGroup;
    }
}
