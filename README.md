
# VVGram 📱

## Description 
VVGram is a Social Media clone project built entirely with Java in a Monolithic architecture. The project aims to replicate the core features of popular social media platforms, providing a base for further development and experimentation.

## Tech Stack </>
- Java
- SpringBoot
- Maven
- Redis
- Spring Security
- Mysql
- JPA for persistance

## What It Does 🤔
VVGram provides the foundational backend for a social media application, including user accounts, posting, and basic social interactions. The current implementation focuses on establishing a Java-based structure that can be extended with additional features.

## Endpoints 🚀
**UserController (/api/users):** 👥
- POST /signup — User signup
- POST /login — User login
- GET /user/{username} — Get user profile by username
- POST / — Search users (with body)
- POST /logout/{token} — Logout user
- PATCH /update/{username} — Update user profile
- POST /follow?user={userOne}&follow={userTwo} — Follow a user
- POST /unfollow?user={userOne}&unfollow={userTwo} — Unfollow a user
- GET /followers/{username} — Get followers of a user
- GET /following/{username} — Get users followed by a user

**PostController (/api/posts):** 💬
- POST /{username} — Upload a post
- PATCH /edit/{username}/{postId} — Edit a post
- DELETE /delete/{username}/{id} — Delete a post
- POST / — Get posts by user (with body)
- GET /{username}/{id} — Get a post by id

**CommentController (/api/comments):** 💬
- POST /{postId}/{username} — Add a comment to a post
- DELETE /{postId}/{commentId} — Delete a comment
- PATCH /{commentId} — Edit a comment
- POST / — Get comments for a post (with body)

**LikeController (/api/likes):** ❤️
- POST /like/{postId}/{username} — Like a post
- POST /unlike/{postId}/{username} — Unlike a post
- POST / — Get likes for a post (with body)

**FeedController (/api/feed/):** 🌐
- GET /home — After login message
- GET /{username} — Get posts for a user feed

## Future Implementation
- Add detailed REST API endpoints and documentation.
- Expand feature set: messaging, notifications, multimedia uploads, etc.
- Improve UI/UX and integrate with front-end frameworks.
- Implement authentication and user privacy controls.
- Dockerize the project
- Add testing suites and CI/CD pipelines.
