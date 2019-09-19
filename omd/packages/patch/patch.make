PATCH := patch
PATCH_VERS := 2.7.6
PATCH_DIR := $(PATCH)-$(PATCH_VERS)

PATCH_BUILD := $(BUILD_HELPER_DIR)/$(PATCH_DIR)-build
PATCH_INSTALL := $(BUILD_HELPER_DIR)/$(PATCH_DIR)-install
PATCH_UNPACK := $(BUILD_HELPER_DIR)/$(PATCH_DIR)-unpack

.PHONY: $(PATCH) $(PATCH)-install $(PATCH)-skel $(PATCH)-clean

$(PATCH): $(PATCH_BUILD)

$(PATCH)-install: $(PATCH_INSTALL)

$(PATCH_BUILD): $(PATCH_UNPACK)
	cd $(PATCH_DIR) && ./configure --prefix=$(OMD_ROOT)
	$(MAKE) -C $(PATCH_DIR)
	$(TOUCH) $@

$(PATCH_INSTALL): $(PATCH_BUILD)
	$(MAKE) DESTDIR=$(DESTDIR) -C $(PATCH_DIR) install
	$(TOUCH) $@

$(PATCH)-skel:

$(PATCH)-clean:
	rm -rf $(PATCH_DIR) $(BUILD_HELPER_DIR)/$(PATCH)*