package com.chivasss.pocket_dimestions.world.sky;

import com.chivasss.pocket_dimestions.weather.WeatherManager;
import com.chivasss.pocket_dimestions.weather.WeatherType;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class SkyColorHandler {

    private static float currentRed = 0.5f;
    private static float currentGreen = 0.7f;
    private static float currentBlue = 1.0f;

    private static final float TARGET_RED_ON = 1.0f;
    private static final float TARGET_GREEN_ON = 0.0f;
    private static final float TARGET_BLUE_ON = 0.0f;

    private static final float TARGET_RED_OFF = 0.5f;
    private static final float TARGET_GREEN_OFF = 0.7f;
    private static final float TARGET_BLUE_OFF = 1.0f;

    private static final float TRANSITION_SPEED = 0.001f;

    @SubscribeEvent
    public static void onSkyRender(net.minecraftforge.client.event.ViewportEvent.ComputeFogColor event) {
        boolean isActive = false;
        if (WeatherManager.getActiveWeather(WeatherType.EMISSION) != null)
            isActive = WeatherManager.getActiveWeather(WeatherType.EMISSION).getWeatherType() == WeatherType.EMISSION;

        float targetRed = isActive ? TARGET_RED_ON : TARGET_RED_OFF;
        float targetGreen = isActive ? TARGET_GREEN_ON : TARGET_GREEN_OFF;
        float targetBlue = isActive ? TARGET_BLUE_ON : TARGET_BLUE_OFF;

        currentRed = approach(currentRed, targetRed, TRANSITION_SPEED);
        currentGreen = approach(currentGreen, targetGreen, TRANSITION_SPEED);
        currentBlue = approach(currentBlue, targetBlue, TRANSITION_SPEED);

        event.setRed(currentRed);
        event.setGreen(currentGreen);
        event.setBlue(currentBlue);
    }

    private static float approach(float current, float target, float delta) {
        if (current < target) {
            return Math.min(current + delta, target);
        } else {
            return Math.max(current - delta, target);
        }
    }
}