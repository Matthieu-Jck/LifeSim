#pragma once

#include <cstdint>

class Component {
public:
    virtual ~Component() = default;

    // Returns the type ID of the component. Each derived component should return a unique ID.
    virtual std::uint32_t getID() const = 0;
};

