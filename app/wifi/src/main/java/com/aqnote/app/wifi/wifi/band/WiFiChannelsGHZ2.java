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

package com.aqnote.app.wifi.wifi.band;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class WiFiChannelsGHZ2 extends WiFiChannels {
    private static final Pair<Integer, Integer> RANGE = new Pair<>(2400, 2499);
    private static final List<Pair<WiFiChannel, WiFiChannel>> SETS = Arrays.asList(
        new Pair<>(new WiFiChannel(1, 2412), new WiFiChannel(13, 2472)),
        new Pair<>(new WiFiChannel(14, 2484), new WiFiChannel(14, 2484)));
    private static final Pair<WiFiChannel, WiFiChannel> SET = new Pair<>(SETS.get(0).first, SETS.get(SETS.size() - 1).second);
    private static final int FREQUENCY_OFFSET = WiFiChannel.FREQUENCY_SPREAD * 2;
    private static final int FREQUENCY_SPREAD = WiFiChannel.FREQUENCY_SPREAD;

    WiFiChannelsGHZ2() {
        super(RANGE, SETS, FREQUENCY_OFFSET, FREQUENCY_SPREAD);
    }

    @Override
    public List<Pair<WiFiChannel, WiFiChannel>> getWiFiChannelPairs() {
        return Arrays.asList(SET);
    }

    @Override
    public Pair<WiFiChannel, WiFiChannel> getWiFiChannelPairFirst(String countryCode) {
        return SET;
    }

    @Override
    public List<WiFiChannel> getAvailableChannels(String countryCode) {
        List<WiFiChannel> wiFiChannels = new ArrayList<>();
        for (int channel : WiFiChannelCountry.find(countryCode).getChannelsGHZ2()) {
            wiFiChannels.add(getWiFiChannelByChannel(channel));
        }
        return wiFiChannels;
    }

    @Override
    public boolean isChannelAvailable(String countryCode, int channel) {
        return WiFiChannelCountry.find(countryCode).isChannelAvailableGHZ2(channel);
    }

    @Override
    public WiFiChannel getWiFiChannelByFrequency(int frequency, @NonNull Pair<WiFiChannel, WiFiChannel> wiFiChannelPair) {
        return isInRange(frequency) ? getWiFiChannel(frequency, SET) : WiFiChannel.UNKNOWN;
    }

}

