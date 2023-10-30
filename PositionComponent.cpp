#include "PositionComponent.h"

PositionComponent::PositionComponent(float xVal, float yVal) : x(xVal), y(yVal) {}

std::uint32_t PositionComponent::getID() const {
    return POSITION_COMPONENT;
}

std::ostream& operator<<(std::ostream& os, const PositionComponent& pos) {
    os << "PositionComponent: x=" << pos.x << ", y=" << pos.y;
    return os;
}
