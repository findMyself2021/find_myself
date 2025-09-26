# Find Myself
### Finding My Own Place  

Implemented in **IntelliJ (Spring Boot, JPA)**  

<img width="400" src="https://user-images.githubusercontent.com/66946182/117409625-58f8ae80-af4c-11eb-810f-0073f528998d.png">

---

## 📌 Overview  

A program that recommends administrative districts (행정동) tailored to user needs by utilizing **public data**.  

---

## ✨ Key Features  

- **Kakao Map API** → Visualizes and colors administrative districts that meet user conditions.  
- **Kakao Login API + JPA** → Login & signup functionality.  
- **Recommendation System** → Suggests districts based on user view counts.  
- **Cluster Analysis** → Recommends districts through data clustering.  
- **Transportation Features**:  
  - **Tmap API** for car & pedestrian routes.  
  - **Odyssey API** for public transportation routes.  

---

## 🖼 Screen Shots  

### 0) Intro Screen  
<img width="800" src="https://user-images.githubusercontent.com/66946182/120468666-6d5d8900-c3dc-11eb-953f-802da7873952.png">

### 1) Main Screen  
<img width="800" src="https://user-images.githubusercontent.com/66946182/117404014-23e85e00-af44-11eb-8eaa-4daf75481875.png">

### 2) Login Screen  
<img width="600" src="https://user-images.githubusercontent.com/66946182/117404975-d836b400-af45-11eb-8127-1c2e92204131.png">

### 3) Search Results  
- Districts that match the conditions are displayed as polygons on the map.  

<img width="800" src="https://user-images.githubusercontent.com/66946182/117405224-3cf20e80-af46-11eb-94b8-c164521f977e.png">  
<img width="800" src="https://user-images.githubusercontent.com/66946182/117405270-5430fc00-af46-11eb-8a0f-c3347980500f.png">  

### 4) Analysis View (when clicking a specific district)  

- **Large screen view:**  
<img width="800" src="https://user-images.githubusercontent.com/66946182/117406019-724b2c00-af47-11eb-9ccc-298473ce813a.png">  
<img width="800" src="https://user-images.githubusercontent.com/66946182/117407307-56488a00-af49-11eb-83ff-b05974515c06.png">  

- **Small screen view:**  
<div>
<img width="49%" src="https://user-images.githubusercontent.com/66946182/117407541-a9bad800-af49-11eb-8346-22f453771d24.png">
<img width="49%" src="https://user-images.githubusercontent.com/66946182/117406347-e554a280-af47-11eb-9603-5bff9f04ed5c.png">
</div>
<div>
<img width="49%" src="https://user-images.githubusercontent.com/66946182/117406826-92c7b600-af48-11eb-9970-df38ea936850.png">
<img width="49%" src="https://user-images.githubusercontent.com/66946182/117407733-edaddd00-af49-11eb-88b0-08253b5e613e.png">
</div>

---

## ⚙ Development Notes  

- When using **push/clone**, token authentication is required for organization repos.  
  👉 [GitHub Token Guide](https://velog.io/@ruddms936/github-토큰-생성)  

- **Administrative District JSON Data Source**:  
  👉 [vuski/admdongkor](https://github.com/vuski/admdongkor)  

---

## 💻 Tech Stack  

- **Backend:** Spring Boot, JPA  
- **Frontend:** JavaScript, Kakao Map API  
- **APIs:** Tmap, Odyssey, Kakao Login  
- **IDE:** IntelliJ  

---

## ⏳ Development Period  

**Dec 2020 – Feb 2021**  
