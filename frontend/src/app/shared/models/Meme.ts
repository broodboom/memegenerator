export class Meme{
  id: number;
  title: string;
  image: string;
  upvotes: number;
  downvotes: number;

  constructor(title: string, image: string, id: number){
    this.title = title;
    this.image = image;
    this.id = id
    this.upvotes = 0;
    this.downvotes = 0;
  }
}