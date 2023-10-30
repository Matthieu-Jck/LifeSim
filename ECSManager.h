#pragma once

#include <vector>
#include <unordered_map>
#include <typeinfo>
#include <memory>
#include <type_traits> 
#include "Entity.h"
#include "System.h"
#include "Component.h"

class ECSManager {
public:
    void addEntity(Entity&& entity) {
        entities.push_back(std::move(entity));
    }

    void addSystem(System* system) {
        systems.push_back(system);
    }

    void updateSystems(float deltaTime) {
        for (System* system : systems) {
            system->update(deltaTime);
        }
    }

    std::vector<Entity>& getAllEntities() {
        return entities;
    }

    std::vector<System*> getAllSystems() {
        return systems;
    }

    template <typename T>
    T* createComponent(T component) {
        static_assert(std::is_base_of<Component, T>::value, "T must inherit from Component");

        // Get or create the storage pool for this component type
        auto& pool = componentPools[typeid(T).name()];

        // Add component to the pool and get pointer to it
        pool.push_back(std::make_unique<T>(component));
        return static_cast<T*>(pool.back().get());
    }

private:
    std::vector<Entity> entities;
    std::vector<System*> systems;
    std::unordered_map<std::string, std::vector<std::unique_ptr<Component>>> componentPools;
};
