# Snippet Saver

* This is a full stack web development project. The purpose of the website is that developers from across the world can access the code snippets which assist during coding and building projects. This project follows microservice architecture. The backend part will be designed with springboot, and the frontend part will be designed with React + Typescript.
* The brief description of the project is it will be a social media of code snippets. Where many users can create their snippets, check out, others copy them and assist self-coding.
* If the above idea goes well, then I will implement a likes and comment system. I have a planning to integrate a chat system with these. Though this project will be a microservice, it is easy to add more and more service.

## Contact me 
[![facebook](./facebook.png)](https://www.facebook.com/abhisek.mohanty.79069/)
[![linkedin](./linkedin.png)](https://www.linkedin.com/in/abhisek-mohanty-3a2241235/)
[![github](./github.png)](https://github.com/abhisekmohantychinua)

### **SnippetSaver Architecture**
![snippetsaver-architecture](./snippetsaver-architecture.png)

### **ORM for Entities**
![snippetsaver-orm](./snippetsaver-orm.png)
#### Problem solved by the above ORM
> While I am trying to Fetch all snippets by userId and Fetch the user by snippetId, it creates a recursion between the json responses. To solve this issue, I have added a field `userId` in `Snippet` which may take an unnecessary space, but it is far better than storing the reference of `Snippet` entity in `User`.It does the both above things with ```Optionl<Snippet> findById(String snippetId)``` and ```List<Snippet> findAllByUserId(String userId)```.