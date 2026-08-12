export interface ProfileData { fullName:string; professionalTitle?:string; shortIntro?:string; description?:string; city?:string; state?:string; country?:string; email?:string; phone?:string; profileImageUrl?:string; resumeUrl?:string }
export interface SkillData { id:number; name:string; category?:string; iconUrl?:string; displayOrder:number }
export interface ProjectData { id:number; slug:string; name:string; shortTitle?:string; thumbnailUrl?:string; shortDescription?:string; projectTitle?:string; projectSubtitle?:string; detailedDescription?:string; liveUrl?:string; githubUrl?:string; displayOrder:number; published:boolean; technologies:{id:number;technologyName:string;displayOrder:number}[]; media:{id:number;mediaType:"IMAGE"|"VIDEO";mediaUrl:string;caption?:string;displayOrder:number}[] }
export interface ContactData { email?:string; githubUrl?:string; linkedinUrl?:string; twitterUrl?:string; whatsapp?:string; instagramUrl?:string; phone?:string }
const REQUEST_TIMEOUT_MS=5000;
async function get<T>(path:string):Promise<T>{
 const controller=new AbortController();
 const timeout=window.setTimeout(()=>controller.abort(),REQUEST_TIMEOUT_MS);
 try{
  const response=await fetch(path,{headers:{Accept:"application/json"},signal:controller.signal,cache:"no-store"});
  if(!response.ok)throw new Error(`Portfolio API ${response.status}`);
  return response.json();
 }finally{window.clearTimeout(timeout)}
}
export const portfolioApi={profile:()=>get<ProfileData>("/api/portfolio/profile"),skills:()=>get<SkillData[]>("/api/portfolio/skills"),projects:()=>get<ProjectData[]>("/api/portfolio/projects"),project:(slug:string)=>get<ProjectData>(`/api/portfolio/projects/${encodeURIComponent(slug)}`),contact:()=>get<ContactData>("/api/portfolio/contact")};
