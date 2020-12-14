import { Tag } from './Tag';

export class Meme{
  id: number;
  title: string;
  description: string;
  imageblob: Blob;
  upvotes: number;
  downvotes: number;
  imageSrc: string;
  categoryId: number;
  userId: number;
  imageId: number;
  tags: Tag[]

  constructor(title: string, image: Blob, id: number){
    this.title = title;
    this.description = '';
    this.imageblob = image;
    this.id = id;
    this.upvotes = 0;
    this.downvotes = 0;
    this.imageSrc = '';
    this.categoryId = 0;
    this.userId = 0;
    this.imageId = 0;
    this.tags = []
  }
}
