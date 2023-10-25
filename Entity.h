#pragma once

#include <unordered_map>
#include <memory>
#include <cstdint>
#include <stdexcept>
#include "Component.h"

class Entity {
public:
    std::unordered_map<std::uint32_t, std::shared_ptr<Component>> components;

    template <typename T, std::enable_if_t<std::is_base_of<Component, T>::value>* = nullptr>
    void addComponent(std::uint32_t componentID, T component);

    template <typename T>
    T& getComponent(std::uint32_t componentID);

    template <typename T>
    const T& getComponent(std::uint32_t componentID) const;
};

template <typename T, std::enable_if_t<std::is_base_of<Component, T>::value>*>
void Entity::addComponent(std::uint32_t componentID, T component) {
    components[componentID] = std::make_shared<T>(component);
}

template <typename T>
T& Entity::getComponent(std::uint32_t componentID) {
    return *std::static_pointer_cast<T>(components[componentID]);
}

template <typename T>
const T& Entity::getComponent(std::uint32_t componentID) const {
    return *std::static_pointer_cast<const T>(components.at(componentID));
}
