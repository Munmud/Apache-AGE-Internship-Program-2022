const express = require("express");
const exp = express();
const port = 3001;
exp.use(express.static("public"));
exp.set("view engine", "html");
exp.get("/", (req, res) => {
  res.render("index");
});
exp.listen(port, () => {
  console.log("Listening on port 3001\n visit : http://localhost:3001/");
});
