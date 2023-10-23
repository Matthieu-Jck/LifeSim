#pragma once
#include <string>
#include <unordered_map>
#include <typeinfo>
#include <stdexcept>
#include "Component.h"
#include <Windows.h>

class Entity {
public:
    template <typename T>
    void addComponent(T component); // You can implement this in the .cpp file

    template <typename T>
    T& getComponent(); // You can implement this in the .cpp file

    template <typename T>
    const T& getComponent() const; // You can implement this in the .cpp file

private:
    std::unordered_map<std::string, void*> components;
};

// Declaration for printEntity, implementation should be in the .cpp file.
void printEntity(const Entity& entity);
