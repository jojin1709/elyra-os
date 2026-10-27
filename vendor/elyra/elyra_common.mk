# Elyra OS common product config (core). Optional parts live in elyra_optional.mk
PRODUCT_BRAND := Elyra
PRODUCT_MANUFACTURER := ElyraOS

# Core First-Party Packages
PRODUCT_PACKAGES += \
    ElyraSecurityCenter \
    ElyraPrivacyCenter \
    ElyraFrameworkOverlay \
    ElyraSystemUIOverlay \
    org.elyra.security-aidl \
    org.elyra.privacy-aidl

# System Properties
PRODUCT_SYSTEM_DEFAULT_PROPERTIES += \
    ro.elyra.version=1.0.0-alpha \
    ro.elyra.tagline="Your device. Your rules." \
    ro.elyra.privacy.strict=true \
    ro.elyra.telemetry.disabled=true
