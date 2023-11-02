#include "Simulation.h"

#pragma warning(push, 0)
#include <SFML/Graphics.hpp>
#include <SFGUI/SFGUI.hpp>
#include <SFGUI/Widgets.hpp>
#pragma warning(pop)

#include "LifeForm.h"

Simulation::Simulation()
    : window(sf::VideoMode(800, 600), "Life Simulation"),
    friction(0.1f), // Example default values
    gravity(9.8f),
    mutationRate(0.01f) {

    window.setFramerateLimit(60);
    initializeGui();

    // Initialize a few life forms
    lifeForms.emplace_back(10.0f, 0.5f, 1.0f, 1.0f, 100.0f, 0.01f); // Parameters are exemplary
    lifeForms.back().setPosition(100, 100); // Position them on the canvas

    lifeForms.emplace_back(15.0f, 0.3f, 0.5f, 1.5f, 150.0f, 0.02f);
    lifeForms.back().setPosition(200, 200);

    // ... add more life forms as needed
}


void Simulation::run() {
    sf::Clock clock;

    while (window.isOpen()) {
        processEvents();
        update(clock.restart());
        render();
    }
}

void Simulation::processEvents() {
    sf::Event event;
    while (window.pollEvent(event)) {
        desktop.HandleEvent(event);
        if (event.type == sf::Event::Closed) {
            window.close();
        }
        // Handle other events...
    }
}

void Simulation::update(sf::Time deltaTime) {
    desktop.Update(deltaTime.asSeconds());
    for (auto& lifeForm : lifeForms) {
        lifeForm.update(deltaTime.asSeconds());
    }
    // Update simulation parameters based on GUI elements values
}

void Simulation::render() {
    window.clear(sf::Color::White); // Clear with white or any other background color

    // Draw life forms
    for (const auto& lifeForm : lifeForms) {
        lifeForm.draw(window);
    }

    // Display the GUI
    sfgui.Display(window);

    window.display();
}


void Simulation::initializeGui() {
    // Create GUI elements and set up callbacks
    guiWindow = sfg::Window::Create(sfg::Window::BACKGROUND | sfg::Window::TITLEBAR);
    guiWindow->SetTitle("Control Panel");
    desktop.Add(guiWindow);

    // Example: Friction scale
    frictionScale = sfg::Scale::Create(sfg::Scale::Orientation::HORIZONTAL);
    frictionScale->SetRange(0.0f, 1.0f);
    frictionScale->SetValue(friction);
    frictionScale->GetAdjustment()->GetSignal(sfg::Adjustment::OnChange).Connect(
        [this] { this->friction = frictionScale->GetValue(); }
    );

    // Create a box to pack widgets vertically
    auto box = sfg::Box::Create(sfg::Box::Orientation::VERTICAL, 5.0f);
    box->Pack(frictionScale);

    // Example buttons
    auto button1 = sfg::Button::Create("Button 1");
    button1->GetSignal(sfg::Widget::OnLeftClick).Connect([this] { /* Button 1 click logic */ });

    auto button2 = sfg::Button::Create("Button 2");
    button2->GetSignal(sfg::Widget::OnLeftClick).Connect([this] { /* Button 2 click logic */ });

    // Pack buttons into the box
    box->Pack(button1);
    box->Pack(button2);

    // ... add other GUI elements and set up the layout

    // Attach the box to the guiWindow
    guiWindow->Add(box);

    // Set the position of the GUI window (sidebar) to the right side
    guiWindow->SetPosition(sf::Vector2f(window.getSize().x - guiWindow->GetAllocation().width, 0));
}


// ... other methods
