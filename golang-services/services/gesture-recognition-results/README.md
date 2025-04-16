# ✋ Gesture Recognition Results Microservice

A microservice in GoLang that collects, stores, and retrieves gesture recognition results for users in the **Russian Sign Language (RSL)** learning platform.

---

## 📦 Features

- `POST /recognitions`: Submit a recognized gesture result.
- `GET /recognitions/{userId}`: Fetch all results for a user.
- Uses in-memory DB for rapid prototyping (swappable with PostgreSQL/Redis).
- UUID-based result IDs and UTC timestamps.
- Fully unit-tested.

---

## 📁 Project Structure

gesture-recognition-results/  
├── go.mod  
├── main.go # Service entry point  
├── internal/  
    │ ├── handler/  
    │ │ ├── recognition.go # HTTP handlers  
    │ │ └── recognition_test.go # Unit tests for handlers  
    │ ├── db/  
    │ │ ├── memory.go # In-memory DB store  
    │ │ └── memory_test.go # DB tests  
    │ └── model/  
    │ └── recognition.go # Recognition result struct  
└── README.md  

---

## 🚀 Getting Started

### 1. Requirements

- Go 1.20+
- `go.work` file linking `gesture-recognition-results`

### 2. Setup

```
# bash
cd golang-services/
go work init ./services/gesture-recognition-results
cd services/gesture-recognition-results
go mod tidy
```
### 3. Run

```
# bash
go run main.go
```