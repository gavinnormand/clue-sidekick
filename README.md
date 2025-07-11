# ClueSidekick 

A web-based assistant for the board game Clue/Cluedo that helps you track cards and deduce the solution through logical elimination.

## Features

- **Smart Deduction Engine**: Automatically deduces which cards players have based on game actions
- **Visual Score Sheet**: Color-coded grid showing definite card locations (red = held, green = solution)
- **Turn Tracking**: Records all guesses and responses throughout the game
- **Failed Guess Support**: Track when players make incorrect final accusations
- **Responsive Design**: Works on desktop and mobile devices

## How It Works

ClueSidekick uses logical deduction to help you solve Clue faster:
- When someone shows a card to another player, it tracks possibilities
- When multiple players can't show cards from a guess, it eliminates those cards from their hands
- When only one card of a type remains unknown, it identifies it as the solution
- Automatically updates as new information is revealed

## Tech Stack

### Frontend
- **Vue 3**
- **TypeScript**
- **Tailwind CSS**
- **Vite**
- Hosted on **Vercel**

### Backend
- **Spring Boot** (Java)
- **RESTful API** design
- **Dockerized** for consistent deployment
- Hosted on **AWS Lightsail**

## How to Use

1. **Setup Your Game**
   - Select which Clue cards are in your game
   - Add player names
   - Select which player you are
   - Select which cards are your cards and any public cards

2. **Track Turns**
   - Record each player's guess
   - Note who showed a card (if anyone)
   - If you're shown a card, specify which one

3. **Watch the Automatic Deductions**
   - Red squares indicate cards that are definitely held by players
   - Green squares indicate potential solution cards
   - The app automatically deduces new information as you play

*Note: This is a fan-made tool and is not affiliated with Hasbro or the official Clue/Cluedo board game.*
