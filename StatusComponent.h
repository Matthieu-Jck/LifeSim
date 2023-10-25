#pragma once

#include "Component.h"
#include <string>

class StatusComponent : public Component {
public:
    // Initialize with default values
    StatusComponent(bool living = true, std::string lifetime = "00:00:00")
        : living(living), lifetime(lifetime) {}

    bool living;         // Indicates whether the entity is alive. Default is true.
    std::string lifetime; // The lifetime of the entity in the format hh:mm:ss. Default is 00:00:00.

    std::uint32_t getID() const override {
        return STATUS_COMPONENT;
    }
};
