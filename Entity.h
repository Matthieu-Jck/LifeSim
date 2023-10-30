#pragma once

#include <unordered_map>
#include <memory>
#include <cstdint>
#include <stdexcept>
#include "Component.h"

class Entity {
public:
    Entity();

    // Move constructor and move assignment operator
    Entity(Entity&& other);
    Entity& operator=(Entity&& other);

    // Deleted copy constructors
    Entity(const Entity& other) = delete;
    Entity& operator=(const Entity& other) = delete;

    template <typename T, std::enable_if_t<std::is_base_of<Component, T>::value>* = nullptr>
    void addComponent(std::uint32_t componentID, std::unique_ptr<T> component);

    template <typename T>
    T* getComponent(std::uint32_t componentID);

    template <typename T>
    const T* getComponent(std::uint32_t componentID) const;

private:
    std::unordered_map<std::uint32_t, std::unique_ptr<Component>> components;
};

// Including the definitions for the templated functions right in the header after the class declaration
template <typename T, std::enable_if_t<std::is_base_of<Component, T>::value>*>
void Entity::addComponent(std::uint32_t componentID, std::unique_ptr<T> component) {
    components[componentID] = std::move(component);
}

template <typename T>
T* Entity::getComponent(std::uint32_t componentID) {
    auto it = components.find(componentID);
    if (it != components.end()) {
        return dynamic_cast<T*>(it->second.get());
    }
    return nullptr;
}

template <typename T>
const T* Entity::getComponent(std::uint32_t componentID) const {
    auto it = components.find(componentID);
    if (it != components.end()) {
        return dynamic_cast<const T*>(it->second.get());
    }
    return nullptr;
}
