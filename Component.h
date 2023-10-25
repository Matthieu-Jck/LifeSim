#pragma once

#include <cstdint>

// Enum for component types
enum ComponentType {
    const std::uint32_t GENE_COMPONENT = 1,
    const std::uint32_t PHYSICS_COMPONENT = 2,
    const std::uint32_t POSITION_COMPONENT = 3,
    const std::uint32_t STATUS_COMPONENT = 4,
    // Add additional components here
};

class Component {
public:
    virtual ~Component() = default;

    // Returns the type ID of the component. Each derived component should return a unique ID.
    virtual std::uint32_t getID() const = 0;
};
