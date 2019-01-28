/*
 * *********************************************************
 *   author   colin
 *   company  telchina
 *   email    wanglin2046@126.com
 *   date     19-1-23 下午2:48
 * ********************************************************
 */

package com.telchina.tharcgiscore.layermgr;

import android.content.Context;

import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.data.Feature;
import com.esri.arcgisruntime.data.FeatureQueryResult;
import com.esri.arcgisruntime.data.QueryParameters;
import com.esri.arcgisruntime.data.ServiceFeatureTable;
import com.esri.arcgisruntime.geometry.Envelope;
import com.esri.arcgisruntime.geometry.Geometry;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.layers.ArcGISMapImageLayer;
import com.esri.arcgisruntime.layers.ArcGISMapImageSublayer;
import com.esri.arcgisruntime.layers.SublayerList;
import com.esri.arcgisruntime.loadable.LoadStatusChangedListener;
import com.telchina.tharcgiscore.GisMapView;
import com.zcolin.frame.util.StringUtil;
import com.zcolin.gui.ZDialog;
import com.zcolin.gui.ZDialogAsyncProgress;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 动态图层管理类
 */
public class MapImageLayerMgr extends AbstractOperationalLayerMgr {

    public MapImageLayerMgr(GisMapView gisMapView) {
        super(gisMapView);
    }

    public void addMapImageLayer(HashMap<String, String> layers) {
        if (layers == null) {
            return;
        }

        Set<Map.Entry<String, String>> entrySet = layers.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            addMapImageLayer(entry.getKey(), entry.getValue());
        }
    }

    public void addMapImageLayer(String layKey, String mapImageLayerUrl) {
        addMapImageLayer(layKey, mapImageLayerUrl, null);
    }

    public void addMapImageLayer(String layKey, String mapImageLayerUrl, LoadStatusChangedListener listener) {
        ArcGISMapImageLayer layer = new ArcGISMapImageLayer(mapImageLayerUrl);
        addMapImageLayer(layKey, layer, listener);
    }

    public void addMapImageLayer(String layKey, ArcGISMapImageLayer layer, LoadStatusChangedListener listener) {
        if (StringUtil.isEmpty(layKey)) {
            layKey = "mapImageLayer";
        }
        addLayer(layKey, layer);
        if (listener != null) {
            layer.addLoadStatusChangedListener(listener);
        }
    }

    public void addMapImageLayerAsync(Context context, String layKey, String mapImageLayerUrl, LoadStatusChangedListener listener) {
        ZDialogAsyncProgress.instance(context).setDoInterface(new ZDialogAsyncProgress.DoInterface() {
            @Override
            public ZDialogAsyncProgress.ProcessInfo onDoInback() {
                addMapImageLayer(layKey, mapImageLayerUrl, listener);
                return null;
            }

            @Override
            public void onPostExecute(ZDialogAsyncProgress.ProcessInfo info) {

            }
        }).show();
    }

    public void addMapImageLayersAsync(Context context, HashMap<String, String> layers, OnLoadFinishListener listener) {
        ZDialogAsyncProgress.instance(context).setDoInterface(new ZDialogAsyncProgress.DoInterface() {
            @Override
            public ZDialogAsyncProgress.ProcessInfo onDoInback() {
                addMapImageLayer(layers);
                return null;
            }

            @Override
            public void onPostExecute(ZDialogAsyncProgress.ProcessInfo info) {
                if (listener != null) {
                    listener.onLoadFinish();
                }
            }
        }).show();
    }


    /**
     * @return 返回该图层下的所有子图层信息
     */
    public SublayerList getMapImageLayerChildLayer(String layerKey) {
        ArcGISMapImageLayer layer = getMapImageLayer(layerKey);
        return layer.getSublayers();
    }


    /**
     * 图例控制集合要素的显示与隐藏
     *
     * @param condition 图例控制的图层及筛选条件
     * @param layerUrls 地图已添加的显示图层url
     *                  <p>
     *                  1、当condition不为空时，按legendLayers种的图例数据控制几何要素的显示
     *                  <p>
     *                  2、当condition为空时，就要显示layers中的所有几何要素
     */
    public void setLegendControl(Map<String, String> condition, List<String> layerUrls) {
        if (condition != null && condition.size() > 0) {
            setLegendControl(condition);
        } else if (layerUrls != null && layerUrls.size() > 0) {
            setLegendControl(layerUrls);
        }
    }

    /**
     * 图例控制集合要素的显示与隐藏
     */
    public void setLegendControl(Map<String, String> condition) {
        if (condition != null && condition.size() > 0) {
            for (Map.Entry<String, String> stringStringEntry : condition.entrySet()) {
                ArcGISMapImageLayer layer = getMapImageLayer(stringStringEntry.getKey());
                if (layer != null && layer.getSublayers() != null) {
                    for (int i = 0; i < layer.getSublayers().size(); i++) {
                        ((ArcGISMapImageSublayer) layer.getSublayers().get(i)).setDefinitionExpression(stringStringEntry.getValue());
                    }
                }
            }
        }
    }

    /**
     * 图例控制集合要素的显示与隐藏
     */
    public void setLegendControl(List<String> layerUrls) {
        if (layerUrls != null && layerUrls.size() > 0) {
            for (String layerUrl : layerUrls) {
                ArcGISMapImageLayer layer = getMapImageLayer(layerUrl);
                if (layer != null && layer.getSublayers() != null) {
                    for (int i = 0; i < layer.getSublayers().size(); i++) {
                        ((ArcGISMapImageSublayer) layer.getSublayers().get(i)).setDefinitionExpression("1=1");
                    }
                    layer.setVisible(true);
                }
            }
        }
    }

    /**
     * 根据layerKey查获取动态图层
     */
    public ArcGISMapImageLayer getMapImageLayer(String layerKey) {
        if (StringUtil.isEmpty(layerKey)) {
            return (ArcGISMapImageLayer) mapLayer.get("mapImageLayer");
        } else {
            return (ArcGISMapImageLayer) mapLayer.get(layerKey);
        }
    }

    /**
     * 获取默认添加的动态图层
     */
    public ArcGISMapImageLayer getDefaultMapImageLayer() {
        return (ArcGISMapImageLayer) mapLayer.get("mapImageLayer");
    }

    /**
     * 根据点坐标查询feature要素
     */
    public Feature getFeatureByPoint(ArcGISMapImageSublayer featureLayer, Point point) {
        FeatureQueryResult featureResult = getFeatureResultByGeometry(featureLayer, new Envelope(point, 10, 10));
        for (Feature feature : featureResult) {
            return feature;
        }
        return null;
    }

    /**
     * 根据位置信息查询获取特征图层元素信息
     */
    public FeatureQueryResult getFeatureResultByGeometry(ArcGISMapImageSublayer layer, Geometry geometry) {
        if (layer == null || geometry == null) {
            return null;
        }

        QueryParameters queryParameters = new QueryParameters();
        queryParameters.setGeometry(geometry);
        ServiceFeatureTable featureTableList = layer.getTable();
        ListenableFuture<FeatureQueryResult> result = featureTableList.queryFeaturesAsync(queryParameters);

        try {
            return result.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据名字模糊查询获取特征图层元素信息
     */
    public FeatureQueryResult getFeatureResultLike(ArcGISMapImageSublayer layer, String key, String value) {
        QueryParameters queryParameters = new QueryParameters();
        queryParameters.setWhereClause(key + " like '%" + value + "%'");
        ServiceFeatureTable featureTableList = layer.getTable();
        Future<FeatureQueryResult> result = featureTableList.queryFeaturesAsync(queryParameters);

        try {
            return result.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据条件搜索, 供给RightMenuFragment使用,多图层一起查询
     *
     * @param onSearchFinishListener 查询完成监听
     */
    public void getFeatureResultLikeAsync(Context context, List<ArcGISMapImageSublayer> layers, String key, String value,
            ZDialog.ZDialogParamSubmitListener<List<FeatureQueryResult>> onSearchFinishListener) {
        ZDialogAsyncProgress.instance(context).setDoInterface(new ZDialogAsyncProgress.DoInterface() {
            @Override
            public ZDialogAsyncProgress.ProcessInfo onDoInback() {
                List<FeatureQueryResult> featureQueryResultList = new ArrayList<>();
                for (ArcGISMapImageSublayer layer : layers) {
                    FeatureQueryResult result = getFeatureResultLike(layer, key, value);
                    featureQueryResultList.add(result);
                }
                ZDialogAsyncProgress.ProcessInfo processInfo = new ZDialogAsyncProgress.ProcessInfo();
                processInfo.info = featureQueryResultList;
                return processInfo;
            }

            @Override
            public void onPostExecute(ZDialogAsyncProgress.ProcessInfo info) {
                if (onSearchFinishListener != null) {
                    onSearchFinishListener.submit((List<FeatureQueryResult>) info.info);
                }
            }
        }).show();
    }

    /**
     * 根据UUID获取特征图层元素信息
     */
    public FeatureQueryResult getFeatureResultEquals(ArcGISMapImageSublayer layer, String key, String value) {
        QueryParameters queryParameters = new QueryParameters();
        queryParameters.setWhereClause(key + "='" + value + "'");
        ServiceFeatureTable featureTableList = layer.getTable();
        Future<FeatureQueryResult> result = featureTableList.queryFeaturesAsync(queryParameters);

        try {
            return result.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取所有特征图层元素信息
     */
    public FeatureQueryResult getAllFeatureResult(ArcGISMapImageSublayer layer) {
        if (layer == null) {
            return null;
        }

        QueryParameters queryParameters = new QueryParameters();
        ServiceFeatureTable featureTableList = layer.getTable();
        Future<FeatureQueryResult> result = featureTableList.queryFeaturesAsync(queryParameters);

        try {
            return result.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 图层加载完毕监听
     */
    public interface OnLoadFinishListener {
        void onLoadFinish();
    }
}
