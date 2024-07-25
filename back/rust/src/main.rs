trait Summary {
  fn summarize(&self);
}

struct User {
  username: String,
  age: u8
}

impl User {
  fn new(username: String, age: u8) -> User {
    User {
      username,
      age
    }
  }
}

impl std::fmt::Display for User {
  fn fmt(&self, f: &mut std::fmt::Formatter) -> std::fmt::Result {
    write!(f, "username: {username}, age: {age}", username = self.username, age = self.age)
  }
}

impl Summary for User {
  fn summarize(&self) {
    println!("User: {}", self);
  }
}

fn main() {
  let mut user = User::new(String::from("Jean"), 18u8);

  user.summarize();

  user.username.push_str(" et Serge");

  user.summarize();

}
