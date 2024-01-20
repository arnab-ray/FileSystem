### Given a flat file of arbirtrary size implement a virtual file system on it.
- The filesystem should support fopen, fread, fwrite, fclose, rename, remove functions.
- Implement a block device interface on the flat file by dividing it into blocks.
- A file in the virtual file system can span across one or more blocks.
- Inodes need to contain metadata about the file eg: name.
- The filesystem has to keep track of blocks that have been allocated to files and those that haven't been.
- A simple program that uses the filesystem to create, write and read files is expected.
- Write locking should be implemented by the filesystem to facilitate concurrent file reads/writes.

### Concepts
- File
  * A logical container for data on a device.
- Inode
  * A container of metadta for an associated file on a device.
- FileSystem
  * A filesystem driver combined with underlying filesystem schema on a block device.
- Block Device
  * A block device is a physical or virtual device that reads and writes data to media in blocks.

### Requirements
- The file system needs to keep track of used and unused blocks on the device.
- Demonstrate ability to read and write files concurrently with out inconsistencies.
- Atleast implement fopen, fwrite, fread and fclose.

### Simplifications
- Use a block of memory instead of flat file if it makes the problem simpler.
- Assume a file can only occupy continuous blocks and cannot be deleted.
- Don't implement write locking of files if time is an issue.
