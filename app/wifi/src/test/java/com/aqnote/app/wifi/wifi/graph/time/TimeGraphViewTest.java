/*
 * WiFi Analyzer
 * Copyright (C) 2016  VREM Software Development <VREMSoftwareDevelopment@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.aqnote.app.wifi.wifi.graph.time;

import android.view.View;

import com.jjoe64.graphview.GraphView;
import com.aqnote.app.wifi.BuildConfig;
import com.aqnote.app.wifi.MainContextHelper;
import com.aqnote.app.wifi.RobolectricUtil;
import com.aqnote.app.wifi.settings.Settings;
import com.aqnote.app.wifi.wifi.band.WiFiBand;
import com.aqnote.app.wifi.wifi.graph.tools.GraphLegend;
import com.aqnote.app.wifi.wifi.graph.tools.GraphViewWrapper;
import com.aqnote.app.wifi.wifi.model.SortBy;
import com.aqnote.app.wifi.wifi.model.WiFiConnection;
import com.aqnote.app.wifi.wifi.model.WiFiData;
import com.aqnote.app.wifi.wifi.model.WiFiDetail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class TimeGraphViewTest {
    private GraphViewWrapper graphViewWrapper;
    private Settings settings;
    private TimeGraphView fixture;

    @Before
    public void setUp() {
        RobolectricUtil.INSTANCE.getMainActivity();

        graphViewWrapper = mock(GraphViewWrapper.class);

        settings = MainContextHelper.INSTANCE.getSettings();

        fixture = new TimeGraphView(WiFiBand.GHZ2);
        fixture.setGraphViewWrapper(graphViewWrapper);
    }

    @After
    public void tearDown() {
        MainContextHelper.INSTANCE.restore();
    }

    @Test
    public void testUpdate() throws Exception {
        // setup
        WiFiData wiFiData = new WiFiData(new ArrayList<WiFiDetail>(), WiFiConnection.EMPTY, new ArrayList<String>());
        withSettings();
        // execute
        fixture.update(wiFiData);
        // validate
        verify(graphViewWrapper).removeSeries(any(Set.class));
        verify(graphViewWrapper).updateLegend(GraphLegend.LEFT);
        verify(graphViewWrapper).setVisibility(View.VISIBLE);
        verifySettings();
    }

    private void verifySettings() {
        verify(settings).getSortBy();
        verify(settings, times(2)).getTimeGraphLegend();
        verify(settings).getWiFiBand();
    }

    private void withSettings() {
        when(settings.getSortBy()).thenReturn(SortBy.SSID);
        when(settings.getTimeGraphLegend()).thenReturn(GraphLegend.LEFT);
        when(settings.getWiFiBand()).thenReturn(WiFiBand.GHZ2);
    }

    @Test
    public void testGetGraphView() throws Exception {
        // setup
        GraphView expected = mock(GraphView.class);
        when(graphViewWrapper.getGraphView()).thenReturn(expected);
        // execute
        GraphView actual = fixture.getGraphView();
        // validate
        assertEquals(expected, actual);
        verify(graphViewWrapper).getGraphView();
    }
}