export class Meme{
  id: number;
  title: string;
  imageblob: Blob;
  upvotes: number;
  downvotes: number;
  imageSrc: string;

  constructor(title: string, image: Blob, id: number){
    this.title = title;
    this.imageblob = image;
    this.id = id
    this.upvotes = 0;
    this.downvotes = 0;
    this.imageSrc = ""
  }
}