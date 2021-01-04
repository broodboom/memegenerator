import { Tag } from "./Tag";

export type Meme = {
  id?: number;
  title: string;
  description?: string;
  imageblob?: Blob;
  upvotes?: number;
  downvotes?: number;
  imageSrc?: string;
  categoryId: number;
  userId: number;
  imageId?: number;
  tags?: Tag[];
};
