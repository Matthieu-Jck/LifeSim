#pragma once

#ifndef SIMULATION_H
#define SIMULATION_H

#pragma warning(push, 0)
#include <SFML/Graphics.hpp>
#include <SFGUI/SFGUI.hpp>
#include <SFGUI/Widgets.hpp>
#pragma warning(pop)

#include "LifeForm.h"
#include <vector>

class Simulation {
public:
    Simulation();
    void run();

private:
    void update(sf::Time deltaTime);
    void render();
    void processEvents();
    void initializeGui();

    sf::RenderWindow window;
    sfg::SFGUI sfgui;
    sfg::Desktop desktop;
    std::vector<LifeForm> lifeForms;

    // GUI elements
    sfg::Window::Ptr guiWindow;
    sfg::Scale::Ptr frictionScale;
    sfg::Scale::Ptr gravityScale;
    sfg::Scale::Ptr mutationRateScale;
    // ... other GUI elements

    // Simulation parameters
    float friction;
    float gravity;
    float mutationRate;
    // ... other parameters
};

#endif // SIMULATION_H
