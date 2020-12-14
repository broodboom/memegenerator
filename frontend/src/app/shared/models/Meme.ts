export class Meme{
  id: number;
  title: string;
  imageblob: Blob;
  likes: number;
  dislikes: number;
  imageSrc: string;

  constructor(title: string, image: Blob, id: number){
    this.title = title;
    this.imageblob = image;
    this.id = id
    this.likes = 0;
    this.dislikes = 0;
    this.imageSrc = ""
  }
}