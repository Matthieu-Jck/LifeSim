#include "Entity.h"
#include <sstream>
#include "PositionComponent.h"
#include <debugapi.h>


void printEntity(const Entity& entity) {
    std::wstringstream ws;
    try {
        const PositionComponent& pos = entity.getComponent<PositionComponent>();
        ws << L"Entity Position: x=" << pos.x << L", y=" << pos.y << L"\n";
    }
    catch (const std::out_of_range&) {
        ws << L"Entity does not have a PositionComponent\n";
    }
    OutputDebugString(ws.str().c_str());
}

template <typename T>
void Entity::addComponent(T component) {
    // Note: This stores a pointer to a temporary, which will lead to undefined behavior
    components[typeid(T).name()] = &component;
}

template <typename T>
T& Entity::getComponent() {
    return *static_cast<T*>(components[typeid(T).name()]);
}

template <typename T>
const T& Entity::getComponent() const {
    return *static_cast<const T*>(components.at(typeid(T).name()));
}
