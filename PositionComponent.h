#pragma once

#include "Component.h"
#include <ostream>

class PositionComponent : public Component {
public:
    float x, y;

    PositionComponent(float x, float y) : x(x), y(y) {}

    std::uint32_t getID() const override {
        return POSITION_COMPONENT;
    }

    friend std::ostream& operator<<(std::ostream& os, const PositionComponent& pos);
};

std::ostream& operator<<(std::ostream& os, const PositionComponent& pos) {
    os << "PositionComponent: x=" << pos.x << ", y=" << pos.y;
    return os;
}
