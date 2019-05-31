LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := promeni
LOCAL_SRC_FILES := promeni.cpp

include $(BUILD_SHARED_LIBRARY)