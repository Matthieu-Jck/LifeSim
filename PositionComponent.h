#pragma once

#include "Component.h"
#include <ostream>

class PositionComponent : public Component {
public:
    float x, y;

    PositionComponent(float x, float y);

    std::uint32_t getID() const override;

    // Declaration of the friend function for output
    friend std::ostream& operator<<(std::ostream& os, const PositionComponent& pos);
};
