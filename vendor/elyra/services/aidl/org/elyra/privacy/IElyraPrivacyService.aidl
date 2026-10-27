package org.elyra.privacy;

import java.util.List;

/**
 * Elyra Privacy Monitor AIDL Interface
 * Real-time hardware kill-switch controller and sensor audit provider.
 */
interface IElyraPrivacyService {
    /** Returns true if the camera hardware kill-switch is active. */
    boolean isCameraKillSwitchActive();

    /** Sets the hardware camera kill-switch state. */
    void setCameraKillSwitchActive(boolean active);

    /** Returns true if the microphone hardware kill-switch is active. */
    boolean isMicKillSwitchActive();

    /** Sets the hardware microphone kill-switch state. */
    void setMicKillSwitchActive(boolean active);

    /** Returns the most recent sensor access events (JSON string format). */
    List<String> getRecentSensorAccessEvents(int maxCount);

    /** Returns clipboard read audit count in the last 24 hours. */
    int getClipboardAccessCountInLast24Hours();
}
